package ohai.newslang.init;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.CrawlerProperties;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.DetailNewsArchive;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.DetailNewsRecommend;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        initService.initDetailNews();
        initService.initOpinion();
        initService.initCrawlerProperties();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder pe;

        public void initCrawlerProperties() {
            CrawlerProperties cp = CrawlerProperties.builder().crawlingDate(LocalDateTime.MIN).crawlingPeriodSecond(600).build();
            em.persist(cp);
        }

        public void initDetailNews() {
            MemberRecommend memberRecommend = MemberRecommend.createMemberRecommend();

            em.persist(Member.builder()
                    .name("오진석")
                    .loginId("ojs")
                    .email("EFGH@gmail.com")
                    .password(pe.encode("1234"))
                    .memberRecommend(memberRecommend)
                    .build());

            DetailNewsArchive news = DetailNewsArchive.builder()
                    .newsUrl("http://dummyUrl1:8080").build();

            DetailNewsRecommend
                    .createNewsRecommend(memberRecommend, news, RecommendStatus.NONE);

            em.persist(news);

        }
        public void initOpinion() {
            MemberRecommend memberRecommend = MemberRecommend.createMemberRecommend();

            Member member = Member.builder()
                    .name("김경민")
                    .loginId("kkm")
                    .email("ABCD@gmail.com")
                    .memberRecommend(memberRecommend)
                    .password(pe.encode("1234")).build();

            em.persist(member);

            DetailNewsArchive news = DetailNewsArchive.builder()
                    .newsUrl("http://dummyUrl2:8080").build();

            em.persist(news);

            Opinion opinion1 = Opinion.createOpinion(member, news, "의견1 의견");

            OpinionRecommend
                    .createOpinionRecommend(memberRecommend,opinion1, RecommendStatus.NONE);

            Opinion opinion2 = Opinion.createOpinion(member, news, "의견2 의견");

            em.persist(opinion1);
            em.persist(opinion2);

        }
    }
}