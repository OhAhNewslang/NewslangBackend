package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NewsRecommend extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "detail_news_archive_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_recommend_id")
    private MemberRecommend memberRecommend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_archive_id")
    private NewsArchive newsArchive;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;

    //연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.getNewsRecommends().add(this);
    }

    public void foreignDetailNewArchive(NewsArchive newNewsArchive) {
        newsArchive = newNewsArchive;
        newsArchive.getNewsRecommends().add(this);
    }

    //비즈니스 로직
    public static NewsRecommend createNewsRecommend(
            MemberRecommend newMemberRecommend,
            NewsArchive newNewsArchive,
            RecommendStatus newStatus) {

        NewsRecommend newsRecommend = new NewsRecommend();
        newsRecommend.foreignMemberRecommend(newMemberRecommend);
        newsRecommend.foreignDetailNewArchive(newNewsArchive);
        newsRecommend.status = newStatus;

        return newsRecommend;
    }

    public void updateStatus(RecommendStatus newStatus) {
        newsArchive.updateLikeCount(countStatus(status, newStatus));
        status = newStatus;

    }

    private int countStatus(RecommendStatus status, RecommendStatus newStatus) {
        // 업데이트 할 추천 정보와 현재 추천 정보가 서로 같으면 0을 리턴해 무시
        // 같지 않다면 새로운 정보가 "LIKE"인지 확인하고 맞으면 OTHER -> LIKE 이므로 1 리턴
        // 아니면 "LIKE" -> OTHER 이므로 -1 리턴
        return status.equals(newStatus) ? 0 :
                newStatus.equals(RecommendStatus.LIKE) ? 1 : -1;
    }

    public static NewsRecommend getNoneRecommend(){
        NewsRecommend newsRecommend = new NewsRecommend();
        newsRecommend.memberRecommend = null;
        newsRecommend.newsArchive = null;
        newsRecommend.status = RecommendStatus.NONE;
        return newsRecommend;
    }
}
