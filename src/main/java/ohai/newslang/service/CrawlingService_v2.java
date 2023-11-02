package ohai.newslang.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.crawling.CrawlingMediaService;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrawlingService_v2 {
    private final CrawlingMediaService crawlingMediaService;
    private final MediaService mediaService;
    private final CategoryService categoryService;
    private final CrawlingNews crawlingNews;
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

    @Bean
    public void crawlingStartMediaList(){
        int start = 0;
        int threadCnt = 3;
        int loopCnt = crawlingMediaList.size() / threadCnt;
        int restCnt = crawlingMediaList.size() % threadCnt;

        for(int i = 0; i < threadCnt; i++){
            int addCnt = 0;
            if (i + 1 == threadCnt){
                addCnt = restCnt;
            }
            crawlingStart("20231014", crawlingMediaList.subList(start, ((i + 1) * loopCnt) + addCnt));
            start = (i + 1) * loopCnt + 1;
        }
    }

    public void crawlingStart(String Date, List<Media> mediaList){
        crawlingNews.run(Date, mediaList);
    }
}
