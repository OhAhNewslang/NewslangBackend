package ohai.newslang;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.CrawlerProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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