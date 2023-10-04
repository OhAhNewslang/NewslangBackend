package ohai.newslang.init;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.ThumbnailNews;
import ohai.newslang.domain.entity.news.News;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.crawling.CrawlingMediaService;
import ohai.newslang.service.crawling.CrawlingNewsService;
import ohai.newslang.service.crawling.NewsArchiveService;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NewsBatchController {

    private final CrawlingNewsService crawlingNewsService;
    private final CrawlingMediaService crawlingMediaService;
    private final NewsArchiveService newsArchiveService;
    private final MediaService mediaService;
    private final CategoryService categoryService;

    private boolean isInitialize = true;

    private int addDay = 0;

    private List<Media> crawlingMediaList = new ArrayList<>();

    @Scheduled(fixedDelay = 5000)
    public void crawlingService() {
        if (isInitialize) {
            // 최초 1회 실행
            // media, category 데이터 저장
            isInitialize = false;
            // media 크롤링
            crawlingMediaList.addAll(crawlingMediaService.getMediaList("https://news.naver.com/main/officeList.naver"));
//            Set<String> set = new HashSet<>();
            // 중복 제거된 category 추출
            List<Category> categoryList = crawlingMediaList.stream()
                    .map(Media::getMediaGroup)
                    .distinct()
//                    .filter(categoryName -> set.add(categoryName))
                    .map(m -> {
                        Category category = new Category();
                        category.setName(m);
                        return category;
                    })
                    .collect(Collectors.toList());

            // media 데이터베이스 저장
            crawlingMediaList.forEach(m ->{
                if (!mediaService.isExistMediaName(m.getName())){
                    mediaService.save(m);
                }
            });
            // cateogry 데이터베이스 저장
            categoryList.forEach(c ->{
                if (!categoryService.isExistCategoryName(c.getName())){
                    categoryService.save(c);
                }
            });
        }

        // 시작 날짜부터 현재날짜까지
        String strStartDate = "20230901";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(strStartDate, dateFormat);
        if (!LocalDate.now().isBefore(startDate.plusDays(this.addDay))){
            startDate = startDate.plusDays(this.addDay);
        }
        String crawlingDate = startDate.format(dateFormat);

        crawlingMediaList.forEach(m -> {
            String parameterId = m.getParameterId();
            int pageNo = 1;
            while (true){
                String category = m.getMediaGroup();
                // 섬네일 뉴스 크롤링
                List<ThumbnailNews> thumbnailNewsList = crawlingNewsService.getNewsList(
                        "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + parameterId + "&date=" + crawlingDate + "&page=" + pageNo);

                // 페이지 번호가 마지막일 때, 마지막 페이지에 데이터가 있을 경우 이전 뉴스 데이터가 계속 표출되었음 (네이버 뉴스 페이지 특성인 것 같음)
                // 다음의 경우를 마지막 페이지라고 생각하여 반복문 종료 조건
                // 1. 마지막 페이지에 뉴스가 없을 경우
                // 2. 마지막 페이지의 뉴스 중 이미 등록된 뉴스가 있을 경우

                if (thumbnailNewsList.size() < 1) break;

                List<String> urlList = thumbnailNewsList.stream()
                        .map(t -> t.getLink())
                        .collect(Collectors.toList());
                List<String> alreadyExistUrl = newsArchiveService.isAlreadyExistUrl(urlList);
                if (alreadyExistUrl.size() > 0)
                    break;
//                if (thumbnailNewsList.size() == alreadyExistUrl.size())
//                    break;

                List<NewsArchive> newsArchiveList = new ArrayList<>();
                thumbnailNewsList.forEach(t ->{
                    if (!alreadyExistUrl.contains(t.getLink())){
                        NewsArchive newsArchive = new NewsArchive(News.builder()
                                .url(t.getLink())
                                .mediaName(t.getMediaName())
                                .categoryName(category)
                                .title(t.getTitle())
                                .contents(t.getSummary())
                                .thumbnailImagePath(t.getImagePath()).build());
                        newsArchiveList.add(newsArchive);
                    }
                });
                if (newsArchiveList.size() > 0)
                    newsArchiveService.saveAll(newsArchiveList);
                pageNo++;
            }
        });

        this.addDay++;
    }
}