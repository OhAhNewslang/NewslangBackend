package ohai.newslang.domain.entity.recommend;

import lombok.Getter;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.enumulate.RecommendStatus;

import javax.persistence.*;

@Entity
@Getter
public class MemberRecommendOpinion {

    @Id
    @GeneratedValue
    @Column(name = "member_recommend_opinion_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long opinionId;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;
}
