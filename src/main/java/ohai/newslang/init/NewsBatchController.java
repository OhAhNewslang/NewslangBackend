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
        /*
        1. 크롤링 시작 날짜 데이터베이스에서 조회
        2. 오늘 날짜랑 시작 날짜 비교하여 같지 않다면 아래를 진행
          2.1 시작 날짜부터 크롤링 진행
          2.2 시작 날짜부터 현재 까지 크롤링 한바퀴 다 돌면 반복 종료
          2.3 크롤링 시작 날짜를 현재 날짜로 변경
        3. 현재 날짜 반복 시작
         */
        if (isInitialize) {
            isInitialize = false;
            crawlingMediaList.addAll(crawlingMediaService.getMediaList("https://news.naver.com/main/officeList.naver"));
            Set<String> set = new HashSet<>();
            List<Category> categoryList = crawlingMediaList.stream()
                    .map(Media::getMediaGroup)
                    .filter(categoryName -> set.add(categoryName))
                    .map(m -> {
                        Category category = new Category();
                        category.setName(m);
                        return category;
                    })
                    .collect(Collectors.toList());

            crawlingMediaList.forEach(m ->{
                if (!mediaService.isExistMediaName(m.getName())){
                    mediaService.save(m);
                }
            });
            categoryList.forEach(c ->{
                if (!categoryService.isExistCategoryName(c.getName())){
                    categoryService.save(c);
                }
            });
        }

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
                List<ThumbnailNews> thumbnailNewsList = crawlingNewsService.getNewsList(
                        "https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + parameterId + "&date=" + crawlingDate + "&page=" + pageNo);
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