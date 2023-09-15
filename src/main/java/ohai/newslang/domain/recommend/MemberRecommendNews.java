package ohai.newslang.domain.recommend;

import lombok.Getter;
import ohai.newslang.domain.Member;

import javax.persistence.*;

@Entity
@Getter
public class MemberRecommendNews {

    @Id
    @GeneratedValue
    @Column(name = "member_recommend_news_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String url;

    @Enumerated(EnumType.STRING)
    private RecommendStatus status;
}
