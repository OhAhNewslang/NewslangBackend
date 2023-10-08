package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.DetailNewArchive;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpinionRecommend extends TimeStamp {

    @Id @GeneratedValue
    @Column(name = "opinion_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_recommend_id")
    private MemberRecommend memberRecommend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opinion_id")
    private Opinion opinion;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;

    //연관 관계 메서드
    public void foreignMemberRecommend(MemberRecommend newMemberRecommend) {
        memberRecommend = newMemberRecommend;
        memberRecommend.getOpinionRecommends().add(this);
    }

    public void foreignOpinion(Opinion newOpinion) {
        opinion = newOpinion;
        opinion.getOpinionRecommends().add(this);
    }

    //비즈니스 로직
    public static OpinionRecommend createOpinionRecommend(MemberRecommend newMemberRecommend,
                                                   Opinion newOpinion,
                                                   RecommendStatus newStatus) {

        OpinionRecommend opinionRecommend = new OpinionRecommend();

        opinionRecommend.foreignMemberRecommend(newMemberRecommend);
        opinionRecommend.foreignOpinion(newOpinion);
        opinionRecommend.updateStatus(newStatus);

        return opinionRecommend;
    }

    public void updateStatus(RecommendStatus newStatus) {
        status = newStatus;
    }
}
