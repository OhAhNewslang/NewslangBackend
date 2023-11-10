package ohai.newslang.service;

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

@Component
@RequiredArgsConstructor
public class InitializeService {

    private final InitService initService;

    @PostConstruct
    public void start(){
        initService.initTestMembers();
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
            Member member = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "뉴스랑",
                    "admin",
                    "admin@newslang.com",
                    pe.encode("admin"));
            member.updateRole(UserRole.ROLE_ADMIN);
            em.persist(member);
            CrawlerProperties cp = CrawlerProperties.builder().crawlingDate(LocalDateTime.MIN).crawlingPeriodSecond(600).build();
            em.persist(cp);
            GptProperties gp = GptProperties.createGptProperties("", "");
            em.persist(gp);
        }

        public void initTestMembers() {

            Member member0 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "오진석",
                    "ojs",
                    "EFGH@gmail.com",
                    pe.encode("1234"));

            Member member1 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "김경민",
                    "kkm",
                    "ABCD@gmail.com",
                    pe.encode("1234"));

            Member member2 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "홍길동",
                    "hjd",
                    "hjd@gmail.com",
                    pe.encode("1234")
            );

            Member member3 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "김이박",
                    "klp",
                    "kip@gmail.com",
                    pe.encode("1234")
            );


            Member member4 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "가나다",
                    "gnd",
                    "gnd@gmail.com",
                    pe.encode("1234")
            );


            Member member5 = Member.createMember(
                    MemberRecommend.createMemberRecommend(),
                    "일이삼",
                    "ils",
                    "ils@gmail.com",
                    pe.encode("1234")
            );

            em.persist(member0);
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);

//            NewsArchive news = NewsArchive.builder()
//                    .url("http://dummyUrl2:8080").build();
//
//            em.persist(news);
//
//            Opinion opinion1 = Opinion.createOpinion(member1, news, "의견1 의견");
//
//            OpinionRecommend
//                    .createOpinionRecommend(member2.getMemberRecommend(), opinion1, RecommendStatus.LIKE);
//
//            Opinion opinion2 = Opinion.createOpinion(member1, news, "의견2 의견");
//
//            OpinionRecommend
//                    .createOpinionRecommend(member3.getMemberRecommend(), opinion2, RecommendStatus.LIKE);
//            OpinionRecommend
//                    .createOpinionRecommend(member4.getMemberRecommend(), opinion2, RecommendStatus.LIKE);
//
//            Opinion opinion3 = Opinion.createOpinion(member1, news, "의견3 의견");
//
//            OpinionRecommend
//                    .createOpinionRecommend(member3.getMemberRecommend(), opinion3, RecommendStatus.LIKE);
//            OpinionRecommend
//                    .createOpinionRecommend(member4.getMemberRecommend(), opinion3, RecommendStatus.LIKE);
//            OpinionRecommend
//                    .createOpinionRecommend(member5.getMemberRecommend(), opinion3, RecommendStatus.LIKE);
//
//            em.persist(opinion1);
//            em.persist(opinion2);
//            em.persist(opinion3);
        }
    }
}