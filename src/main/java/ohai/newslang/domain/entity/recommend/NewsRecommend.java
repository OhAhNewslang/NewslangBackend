package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.news.DetailNewArchive;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;
import ohai.newslang.repository.recommand.NewsRecommendRepository;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsRecommend extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "news_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_recommend_id")
    private MemberRecommend memberRecommend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_news_archive_id")
    private DetailNewArchive detailNewArchive;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;



    //연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.getNewsRecommends().add(this);
    }

    public void foreignDetailNewArchive(DetailNewArchive newDetailNewArchive) {
        detailNewArchive = newDetailNewArchive;
        detailNewArchive.getNewsRecommends().add(this);
    }

    //비즈니스 로직
    public static NewsRecommend createNewsRecommend(MemberRecommend newMemberRecommend,
                                                    DetailNewArchive newDetailNewArchive,
                                                    RecommendStatus newStatus) {

        NewsRecommend newsRecommend = new NewsRecommend();

        newsRecommend.foreignMemberRecommend(newMemberRecommend);
        newsRecommend.foreignDetailNewArchive(newDetailNewArchive);
        newsRecommend.updateStatus(newStatus);

        return newsRecommend;
    }

    public void updateStatus(RecommendStatus newStatus) {
        status = newStatus;
    }
}
