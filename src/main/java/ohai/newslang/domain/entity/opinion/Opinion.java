package ohai.newslang.domain.entity.opinion;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.DetailNewArchive;
import ohai.newslang.domain.entity.recommend.MemberRecommend;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import org.hibernate.IdentifierLoadAccess;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Opinion extends TimeStamp {

    @Id @GeneratedValue
    @Column(name = "opinion_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private DetailNewArchive detailNewArchive;

    @OneToMany(mappedBy = "opinion", cascade = CascadeType.ALL)
    @JoinColumn(name = "opinion_recommend_id")
    private List<OpinionRecommend> opinionRecommends = new ArrayList<>();

    //연관 관계 메서드
    private void foreignMember(Member newMember) {
        member = newMember;
        member.getOpinions().add(this);
    }

    private void foreignDetailNewArchive(DetailNewArchive newDetailNewArchive) {
        detailNewArchive = newDetailNewArchive;
        detailNewArchive.getOpinions().add(this);
    }

    private void eraseForeignKey() {
        member.getOpinions().remove(this);
        detailNewArchive.getOpinions().remove(this);
        member = null;
        detailNewArchive = null;
    }

    //비즈니스 로직
    public static Opinion createOpinion(String newContent, Member newMember,DetailNewArchive newDetailNewArchive){
        Opinion opinion = new Opinion();
        opinion.content = newContent;

        opinion.foreignMember(newMember);
        opinion.foreignDetailNewArchive(newDetailNewArchive);

        return opinion;
    }

    public void updateContent(String newContent) {
        content = newContent;
    }

    public Opinion deleteOpinion() {
        this.eraseForeignKey();
        return this;
    }
}
