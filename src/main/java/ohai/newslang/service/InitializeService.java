package ohai.newslang.service;

import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.properties.CrawlerProperties;
import ohai.newslang.domain.entity.properties.GptProperties;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.domain.enumulate.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InitializeService {

    private final InitService initService;

    @PostConstruct
    public void start(){
        initService.initAdminProperties();
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
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder pe;

        public void initAdminProperties() {
            List<Member> memberList = em.createQuery("select m from Member m where m.loginId = 'admin'", Member.class).getResultList();
            if (memberList.size() < 1) {
                Member member = Member.createMember(
                        MemberRecommend.createMemberRecommend(),
                        "뉴스랑",
                        "admin",
                        "admin@newslang.com",
                        pe.encode("admin"));
                member.updateRole(UserRole.ROLE_ADMIN);
                em.persist(member);
                CrawlerProperties cp = CrawlerProperties.builder()
                        .crawlingDate(LocalDateTime.of(1970, 1, 1, 0, 0, 0, 1))
                        .crawlingPeriodSecond(600).build();
                em.persist(cp);
                GptProperties gp = GptProperties.createGptProperties("", "");
                em.persist(gp);
            }
        }
    }
}