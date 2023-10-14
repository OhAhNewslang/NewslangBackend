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

            em.persist(Member.createMember(
                    memberRecommend,
                    "오진석",
                    "ojs",
                    "EFGH@gmail.com",
                    pe.encode("1234")
            ));

            DetailNewsArchive news = DetailNewsArchive.builder()
                    .newsUrl("http://dummyUrl1:8080").build();

            DetailNewsRecommend
                    .createNewsRecommend(memberRecommend, news, RecommendStatus.NONE);

            em.persist(news);

        }
        public void initOpinion() {

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

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            em.persist(member4);
            em.persist(member5);

            DetailNewsArchive news = DetailNewsArchive.builder()
                    .newsUrl("http://dummyUrl2:8080").build();

            em.persist(news);

            Opinion opinion1 = Opinion.createOpinion(member1, news, "의견1 의견");

            OpinionRecommend
                    .createOpinionRecommend(member2.getMemberRecommend(),opinion1, RecommendStatus.LIKE);

            Opinion opinion2 = Opinion.createOpinion(member1, news, "의견2 의견");

            OpinionRecommend
                    .createOpinionRecommend(member3.getMemberRecommend(),opinion2, RecommendStatus.LIKE);
            OpinionRecommend
                    .createOpinionRecommend(member4.getMemberRecommend(),opinion2, RecommendStatus.LIKE);

            Opinion opinion3 = Opinion.createOpinion(member1, news, "의견3 의견");

            OpinionRecommend
                    .createOpinionRecommend(member3.getMemberRecommend(),opinion3, RecommendStatus.LIKE);
            OpinionRecommend
                    .createOpinionRecommend(member4.getMemberRecommend(),opinion3, RecommendStatus.LIKE);
            OpinionRecommend
                    .createOpinionRecommend(member5.getMemberRecommend(),opinion3, RecommendStatus.LIKE);

            em.persist(opinion1);
            em.persist(opinion2);
            em.persist(opinion3);


        }
    }
}