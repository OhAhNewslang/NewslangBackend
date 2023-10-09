package ohai.newslang.domain.entity.recommend;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;

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

    @OneToMany(mappedBy = "memberRecommend", cascade = CascadeType.ALL)
    private List<OpinionRecommend> opinionRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "memberRecommend", cascade = CascadeType.ALL)
    private List<DetailNewsRecommend> detailNewsRecommends = new ArrayList<>();

    //연관 관계 메서드
    public void foreignMember(Member newMember) {
        member = newMember;
    }

    //비즈니스 로직
}
