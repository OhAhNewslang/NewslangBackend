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
//        initService.initMember();
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

//        public void initMember(){
//            Member member1 = createMember("김경민", "Path1", LocalDateTime.now());
//            em.persist(member1);
//        }
//
//        private static Member createMember(String name, String imagePath, LocalDateTime joinTime){
//            Member member = new Member();
//            member.setName(name);
//            member.setImagePath(imagePath);
//            member.setJoinDate(joinTime);
//            return member;
//        }
    }
}