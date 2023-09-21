package ohai.newslang;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.subscribe.SubscribeItemRepository;
import ohai.newslang.service.crawling.MediaCrawlingServiceImpl;
import ohai.newslang.service.crawling.ThumbnailNewsCrawlingServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class NewsBatchController {

    private final ThumbnailNewsCrawlingServiceImpl detailNewsCrawlingService;
    private final SubscribeItemRepository subscribeItemRepository;

    private int displayCount = 100;
    private int startIndex = 1;
    private boolean isFirstCrawling = true;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedDelay = 5000)
    public void newsCrawAndSave() {

        /*
        1. 카테고리 이름 -> oid 필요
        2. 중복된 url 검색시 다음 카테고리 혹은 다음 날짜로 진행
        3. 최종 반복 종료는 카테고리 이름 리스트를 다 순회했을 때
        4. NewsArchive 데이터베이스에 저장
        5. NewsArchive 데이터베이스에는 thumbnail news 저장 -> 뉴스 상세보기 클릭시 url 활용하여 단건 크롤링 진행
         */

        int categoryNo = 0;
        int pageNo = 1;
        while (true){
            boolean isNextParameter = crawling("009", "20230921", pageNo);
            pageNo++;
        }
    }

    private boolean crawling(String category, String date, int page){
        List<ThumbnailNews> thumbnailNewsList = detailNewsCrawlingService.crawlingThumbnailNews("https://news.naver.com/main/list.naver?mode=LPOD&mid=sec&oid=" + category + "&date=" + date + "&page=" + page);

        return false;
    }

    private List<String> getCategory(){
        List<SubscribeItem> subscribeItems = subscribeItemRepository.findAllWithEntityType(Category.class);
        return subscribeItems.stream()
                .map(s -> s.getName())
                .collect(Collectors.toList());
    }
}
