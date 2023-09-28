package ohai.newslang.domain.recommend;

import lombok.Getter;
import ohai.newslang.domain.Member;

import javax.persistence.*;

@Entity
@Getter
public class MemberRecommendComment {

    @Id
    @GeneratedValue
    @Column(name = "member_recommend_comment_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long commentId;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;
}
