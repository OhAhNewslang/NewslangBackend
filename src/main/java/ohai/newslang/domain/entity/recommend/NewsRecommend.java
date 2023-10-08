package ohai.newslang.domain.entity.recommend;

import lombok.Getter;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;

@Entity
@Getter
public class NewsRecommend {

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
