package ohai.newslang;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.CrawlerProperties;
import ohai.newslang.domain.Member;
import ohai.newslang.domain.News;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.Media;
import ohai.newslang.service.crawling.MediaCrawlingServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitializeDatabase {

    private final InitService initService;

    @PostConstruct
    public void start(){
        initService.initMember();
        initService.initMedia();
        initService.initCategory();
        initService.initCrawlerProperties();
        initService.initNews();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final MediaCrawlingServiceImpl mediaCrawlingService;

        public void initCrawlerProperties(){
            CrawlerProperties cp = CrawlerProperties.builder().crawlingDate(LocalDateTime.MIN).crawlingPeriodSecond(600).build();
            em.persist(cp);
        }

        public void initNews(){
            NewsArchive na1 = new NewsArchive(
                    News.builder().url("http://www.aaa123.com").mediaName("매일경제").categoryName("경제").writer("박진주").title("이대로 괜찮은가").contents("정말 이대로 괜찮은가").build()
            );
            NewsArchive na2 = new NewsArchive(
                    News.builder().url("http://www.aaa456.com").mediaName("조선일보").categoryName("정치").writer("김수빈").title("정치의 혁신").contents("정치는 어떻게 해야하는가").build()
            );
            NewsArchive na3 = new NewsArchive(
                    News.builder().url("http://www.aaa789.com").mediaName("농민신문").categoryName("경제").writer("박진주").title("이대로 괜찮은가").contents("정말 이대로 괜찮은가").build()
            );
            NewsArchive na4 = new NewsArchive(
                    News.builder().url("http://www.aaa999.com").mediaName("스포츠동아").categoryName("스포츠").writer("김동희").title("손흥민의 질주").contents("정말 빠른 손흥민의 질주").build()
            );
            NewsArchive na5 = new NewsArchive(
                    News.builder().url("http://www.aaa000.com").mediaName("전자신문").categoryName("IT").writer("이상준").title("CHAT GPT의 미래는").contents("CHAT GPT의 기술적 한계").build()
            );
            em.persist(na1);
            em.persist(na2);
            em.persist(na3);
            em.persist(na4);
            em.persist(na5);
        }

        public void initMember(){
            Member member1 = createMember("김경민", "Path1", LocalDateTime.now());
            em.persist(member1);
            Member member2 = createMember("이진호","Path2", LocalDateTime.now());
            em.persist(member2);
            Member member3 = createMember("김수진","Path3", LocalDateTime.now());
            em.persist(member3);
            Member member4 = createMember("박수빈","Path4", LocalDateTime.now());
            em.persist(member4);
        }

        public void initMedia(){
            List<Media> mediaList = mediaCrawlingService.crawlingMedia("https://news.naver.com/main/officeList.naver");

//            Media media1 = createMedia("매일경제", "Path1");
//            em.persist(media1);
//            Media media2 = createMedia("조선일보", "Path2");
//            em.persist(media2);
//            Media media3 = createMedia("중앙일보", "Path3");
//            em.persist(media3);
//            Media media4 = createMedia("동아일보", "Path4");
//            em.persist(media4);
//            Media media5 = createMedia("농민신문", "Path5");
//            em.persist(media5);
//            Media media6 = createMedia("한국경제", "Path6");
//            em.persist(media6);
//            Media media7 = createMedia("문화일보", "Path6");
//            em.persist(media7);
//            Media media8 = createMedia("스포츠동아", "Path6");
//            em.persist(media8);
//            Media media9 = createMedia("스포츠서울", "Path6");
//            em.persist(media9);
//            Media media10 = createMedia("스포츠조선", "Path6");
//            em.persist(media10);
//            Media media11 = createMedia("국민일보", "Path6");
//            em.persist(media11);
//            Media media12 = createMedia("서울경제신문", "Path6");
//            em.persist(media12);
//            Media media13 = createMedia("전자신문", "Path6");
//            em.persist(media13);
        }

        public void initCategory(){
//            Category category1 = createCategory("정치");
//            em.persist(category1);
//            Category category2 = createCategory("경제");
//            em.persist(category2);
//            Category category3 = createCategory("문화");
//            em.persist(category3);
//            Category category4 = createCategory("사회");
//            em.persist(category4);
//            Category category5 = createCategory("IT");
//            em.persist(category5);
//            Category category6 = createCategory("스포츠");
//            em.persist(category6);
//            Category category7 = createCategory("연애");
//            em.persist(category7);
        }

        private static Member createMember(String name, String imagePath, LocalDateTime joinTime){
            Member member = new Member();
            member.setName(name);
            member.setImagePath(imagePath);
            member.setJoinDate(joinTime);
            return member;
        }

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
