package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
        detailNewsRecommend.status = newStatus;

        return detailNewsRecommend;
    }

    public void updateStatus(RecommendStatus newStatus) {
        detailNewsArchive.updateLikeCount(countStatus(status, newStatus));
        status = newStatus;

    }

    private int countStatus(RecommendStatus status, RecommendStatus newStatus) {
        // 업데이트 할 추천 정보와 현재 추천 정보가 서로 같으면 0을 리턴해 무시
        // 같지 않다면 새로운 정보가 "LIKE"인지 확인하고 맞으면 OTHER -> LIKE 이므로 1 리턴
        // 아니면 "LIKE" -> OTHER 이므로 -1 리턴
        return status.equals(newStatus) ? 0 :
                newStatus.equals(RecommendStatus.LIKE) ? 1 : -1;
    }
}
