package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.enumulate.RecommendStatus;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRecommend extends TimeStamp {

    @Id
    @GeneratedValue
    @Column(name = "member_recommend_id")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "member_recommend", cascade = CascadeType.ALL)
    @JoinColumn(name = "opinion_recommend_id")
    private List<OpinionRecommend> opinionRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "member_recommend", cascade = CascadeType.ALL)
    @JoinColumn(name = "news_recommend_id")
    private List<NewsRecommend> newsRecommends = new ArrayList<>();

    //연관 관계 메서드
    public void foreignMember(Member newMember) {
        member = newMember;
        member.foreignMemberRecommend(this);
    }

    //비즈니스 로직
}
