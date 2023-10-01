package ohai.newslang.init;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.CrawlerProperties;
import ohai.newslang.domain.entity.member.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

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
            Member member1 = createMember("김경민", "ABCD@gmail.com", "1234", "USER", "AAAA");
            em.persist(member1);
        }

        private static Member createMember(String name, String email, String password, String roles, String imagePath){
            Member member = new Member(name, email, password, roles, imagePath);
            return member;
        }
    }
}