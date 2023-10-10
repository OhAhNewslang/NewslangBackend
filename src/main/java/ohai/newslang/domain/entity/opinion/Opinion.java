package ohai.newslang.domain.entity.opinion;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.DetailNewsArchive;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;

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

    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detail_news_id")
    private DetailNewsArchive detailNewsArchive;

    @OneToMany(mappedBy = "opinion", cascade = CascadeType.ALL)
    private List<OpinionRecommend> opinionRecommends = new ArrayList<>();

    //생성자
    @Builder
    public Opinion(Member member, DetailNewsArchive detailNewsArchive, String content, int likeCount) {
        this.member = member;
        this.detailNewsArchive = detailNewsArchive;
        this.content = content;
        this.likeCount = likeCount;
    }

    //연관 관계 메서드
    private void foreignMember(Member newMember) {
        member = newMember;
        member.getOpinions().add(this);
    }

    private void foreignDetailNewArchive(DetailNewsArchive newDetailNewsArchive) {
        detailNewsArchive = newDetailNewsArchive;
        detailNewsArchive.getOpinions().add(this);
    }

    private void eraseForeignKey() {
        member.getOpinions().remove(this);
        detailNewsArchive.getOpinions().remove(this);
        member = null;
        detailNewsArchive = null;
    }

    //비즈니스 로직
    public static Opinion createOpinion(Member newMember, DetailNewsArchive newDetailNewsArchive, String newContent){
        Opinion opinion = new Opinion();
        opinion.content = newContent;
        opinion.likeCount = 0;

        opinion.foreignMember(newMember);
        opinion.foreignDetailNewArchive(newDetailNewsArchive);

        return opinion;
    }

    public void updateContent(String newContent) {
        content = newContent;
    }

    public void updateLikeCount(int count) {
        likeCount += count;
        checkCount(likeCount);
    }

    // 추천 수가 0 밑으로 내려가면 0으로 초기화
    private void checkCount(int newCount) {
        likeCount = Math.max(newCount, 0);
    }

    public Opinion deleteOpinion() {
        this.eraseForeignKey();
        return this;
    }
}
