package ohai.newslang.service.crawling;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.news.NewsArchiveService;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final CrawlingMediaService crawlingMediaService;
    private final MediaService mediaService;
    private final CategoryService categoryService;
    private final NewsArchiveService newsArchiveService;
    private final CrawlingNewsService crawlingNewsService;
    private List<Media> crawlingMediaList = new ArrayList<>();

    @PostConstruct
    public void crawlingMedia(){
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

//    @Bean
//    public void runCrawl() {
//        Thread t = new Thread(new CrawlingNews(newsArchiveService, crawlingNewsService, "20231101", crawlingMediaList));
//        t.start();
//    }

    @Bean
    public void runCrawl() {
        String startDate = "20231101";
        int threadCount = 5;
        int start = 0;
        int loopCnt = crawlingMediaList.size() / threadCount;
        int restCnt = crawlingMediaList.size() % threadCount;
        Thread[] t_crawl = new Thread[threadCount];

        for(int i = 0; i < threadCount; i++){
            int addCnt = 0;
            if (i + 1 == threadCount){
                addCnt = restCnt;
            }
            List<Media> mediaList = crawlingMediaList.subList(start, ((i + 1) * loopCnt) + addCnt);
            Runnable r = new CrawlingNews(newsArchiveService, crawlingNewsService, startDate, mediaList);
            t_crawl[i] = new Thread(r);
            t_crawl[i].start();

            start = (i + 1) * loopCnt + 1;
        }
    }
}
