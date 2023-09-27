package ohai.newslang;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.CrawlerProperties;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.SubscribeCategory;
import ohai.newslang.domain.subscribe.reference.Category;
import ohai.newslang.domain.subscribe.reference.Media;
import ohai.newslang.service.crawling.MediaCrawlingServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializeDatabase {

    private final InitService initService;

    @PostConstruct
    public void start(){
        initService.initMember();
        initService.initCrawlerProperties();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void initCrawlerProperties(){
            CrawlerProperties cp = CrawlerProperties.builder().crawlingDate(LocalDateTime.MIN).crawlingPeriodSecond(600).build();
            em.persist(cp);
        }

        public void initMember(){
//            Member member1 = createMember("김경민", "Path1", LocalDateTime.now());
//            em.persist(member1);
        }

//        private static Member createMember(String name, String imagePath, LocalDateTime joinTime){
//            Member member = new Member();
//            member.setName(name);
//            member.setImagePath(imagePath);
//            member.setJoinDate(joinTime);
//            return member;
//        }

        public void initMedia(){
            Media media1 = createMedia("매일경제", "Path1");
            em.persist(media1);
            Media media2 = createMedia("조선일보", "Path2");
            em.persist(media2);
            Media media3 = createMedia("중앙일보", "Path3");
            em.persist(media3);
            Media media4 = createMedia("동아일보", "Path4");
            em.persist(media4);
            Media media5 = createMedia("농민신문", "Path5");
            em.persist(media5);
            Media media6 = createMedia("한국경제", "Path6");
            em.persist(media6);
            Media media7 = createMedia("문화일보", "Path6");
            em.persist(media7);
            Media media8 = createMedia("스포츠동아", "Path6");
            em.persist(media8);
            Media media9 = createMedia("스포츠서울", "Path6");
            em.persist(media9);
            Media media10 = createMedia("스포츠조선", "Path6");
            em.persist(media10);
            Media media11 = createMedia("국민일보", "Path6");
            em.persist(media11);
            Media media12 = createMedia("서울경제신문", "Path6");
            em.persist(media12);
            Media media13 = createMedia("전자신문", "Path6");
            em.persist(media13);
        }

        public void initCategory(){
            Category category1 = createCategory("정치");
            em.persist(category1);
            Category category2 = createCategory("경제");
            em.persist(category2);
            Category category3 = createCategory("문화");
            em.persist(category3);
            Category category4 = createCategory("사회");
            em.persist(category4);
            Category category5 = createCategory("IT");
            em.persist(category5);
            Category category6 = createCategory("스포츠");
            em.persist(category6);
            Category category7 = createCategory("연애");
            em.persist(category7);
        }

//        private static Member createMember(String name, String imagePath, LocalDateTime joinTime){
//            Member member = new Member();
//            member.setName(name);
//            member.setImagePath(imagePath);
//            member.setJoinDate(joinTime);
//            return member;
//        }

        private static Media createMedia(String name, String imagePath){
            Media media = new Media();
            media.setName(name);
            media.setImagePath(imagePath);
            return media;
        }

        private static Category createCategory(String name){
            Category category = new Category();
            category.setName(name);
            return category;
        }
    }
}
