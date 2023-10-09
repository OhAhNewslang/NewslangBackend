package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.news.DetailNewsArchive;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailNewsRecommend extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "detail_news_archive_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_recommend_id")
    private MemberRecommend memberRecommend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_news_archive_id")
    private DetailNewsArchive detailNewsArchive;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;

    //연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.getDetailNewsRecommends().add(this);
    }

    public void foreignDetailNewArchive(DetailNewsArchive newDetailNewsArchive) {
        detailNewsArchive = newDetailNewsArchive;
        detailNewsArchive.getDetailNewsRecommends().add(this);
    }

    //비즈니스 로직
    public static DetailNewsRecommend createNewsRecommend(MemberRecommend newMemberRecommend,
                                                          DetailNewsArchive newDetailNewsArchive,
                                                          RecommendStatus newStatus) {

        DetailNewsRecommend detailNewsRecommend = new DetailNewsRecommend();

        detailNewsRecommend.foreignMemberRecommend(newMemberRecommend);
        detailNewsRecommend.foreignDetailNewArchive(newDetailNewsArchive);
        detailNewsRecommend.updateStatus(newStatus);

        return detailNewsRecommend;
    }

    public void updateStatus(RecommendStatus newStatus) {
        status = newStatus;
    }
}
