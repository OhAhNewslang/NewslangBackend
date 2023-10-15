package ohai.newslang.service;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.CrawlerProperties;
import ohai.newslang.domain.entity.member.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitializeService {

    private final InitService initService;

    @PostConstruct
    public void start(){
        initService.initMember();
        initService.initCrawlerProperties();
    }

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return p -> {
            p.setOneIndexedParameters(true);    // 1 페이지 부터 시작
        };
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder pe;

        public void initCrawlerProperties(){
            CrawlerProperties cp = CrawlerProperties.builder().crawlingDate(LocalDateTime.MIN).crawlingPeriodSecond(600).build();
            em.persist(cp);
        }

        public void initMember(){
            Member member1 = createMember("김경민","kkm" ,"ABCD@gmail.com", pe.encode("1234"));
            Member member2 = createMember("오진석","ojs" ,"EFGH@gmail.com", pe.encode("1234"));
            em.persist(member1);
            em.persist(member2);
        }

        private static Member createMember(String name, String loginId, String email, String password){
            Member member = new Member(name, loginId, email, password);
            return member;
        }
    }
}