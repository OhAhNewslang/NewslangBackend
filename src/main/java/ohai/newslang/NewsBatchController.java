package ohai.newslang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.News;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.reference.Category;
import ohai.newslang.domain.subscribe.reference.Media;
import ohai.newslang.service.NewsArchiveService;
import ohai.newslang.service.crawling.MediaCrawlingServiceImpl;
import ohai.newslang.service.crawling.ThumbnailNewsCrawlingServiceImpl;
import ohai.newslang.service.subscribe.CategoryService;
import ohai.newslang.service.subscribe.MediaService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Component
public class NewsBatchController {

    private final ThumbnailNewsCrawlingServiceImpl detailNewsCrawlingService;
    private final NewsArchiveService newsArchiveService;
    private final MediaCrawlingServiceImpl mediaCrawlingService;
    private final MediaService mediaService;
    private final CategoryService categoryService;

    private boolean isFirstStart = true;

    @Scheduled(fixedDelay = 5000)
    public void newsCrawAndSave() {
        /*
        1. 크롤링 시작 날짜 데이터베이스에서 조회
        2. 오늘 날짜랑 시작 날짜 비교하여 같지 않다면 아래를 진행
          2.1 시작 날짜부터 크롤링 진행
          2.2 시작 날짜부터 현재 까지 크롤링 한바퀴 다 돌면 반복 종료
          2.3 크롤링 시작 날짜를 현재 날짜로 변경
        3. 현재 날짜 반복 시작
         */

        if (isFirstStart) {
            isFirstStart = false;
            initializeMedia();
        }
        String startDate = "20230901";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDate.now().format(dateFormat);
        List<Media> mediaList = mediaService.findSubscribeItemList();

        mediaList.forEach(m -> {
            String parameterId = m.getParameterId();
            int pageNo = 1;
            while (true){
                try
                {
                    crawling(m.getMediaGroup(), parameterId, date, pageNo);
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                    break;
                }
                pageNo++;
            }
        });
    }

    private void crawling(String categoryName, String category, String date, int page) throws Exception{
        /*
        1. 카테고리 이름 -> oid 필요
        2. 중복된 url 검색시 다음 카테고리 혹은 다음 날짜로 진행
        3. 최종 반복 종료는 카테고리 이름 리스트를 다 순회했을 때
        4. NewsArchive 데이터베이스에 저장
        5. NewsArchive 데이터베이스에는 thumbnail news 저장 -> 뉴스 상세보기 클릭시 url 활용하여 단건 크롤링 진행
         */

        List<ThumbnailNews> thumbnailNewsList = detailNewsCrawlingService.crawlingThumbnailNews("https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + category + "&date=" + date + "&page=" + page);
        if (thumbnailNewsList.size() < 1) throw new Exception("Not exist news");

        thumbnailNewsList.forEach(t ->{
            NewsArchive newsArchive = new NewsArchive(News.builder().url(t.getLink())
                    .mediaName(t.getMediaName())
                    .categoryName(categoryName)
                    .title(t.getTitle())
                    .contents(t.getSummary())
                    .thumbnailImagePath(t.getImagePath()).build());
            newsArchiveService.save(newsArchive);
        });
    }

    private void initializeMedia(){
        List<Media> mediaList = mediaCrawlingService.crawlingMedia("https://news.naver.com/main/officeList.naver");
        HashSet<String> categoryList = new HashSet<>();
        for (Media media : mediaList) {
            try {
                mediaService.save(media);
            }catch (Exception ex){
            }
            categoryList.add(media.getMediaGroup());
        }
        for (String categoryName : categoryList) {
            try {
                Category category = new Category();
                category.setName(categoryName);
                categoryService.save(category);
            }catch (Exception ex){

            }
        }
    }
}