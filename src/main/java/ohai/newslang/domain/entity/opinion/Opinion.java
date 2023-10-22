package ohai.newslang.domain.entity.opinion;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Opinion extends TimeStamp {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "opinion_id")
    private Long  id;

    @Column(updatable = false)
    private String uuid;

    @Column(nullable = false)
    private String content;

    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_archive_id")
    private NewsArchive newsArchive;

    @OneToMany(mappedBy = "opinion", cascade = CascadeType.ALL)
    private List<OpinionRecommend> opinionRecommends = new ArrayList<>();

    @PrePersist
    private void createUUID(){
        uuid = String.valueOf(UUID.randomUUID());
    }

    //생성자
    @Builder
    public Opinion(Member member, NewsArchive newsArchive, String content, int likeCount) {
        this.member = member;
        this.newsArchive = newsArchive;
        this.content = content;
        this.likeCount = likeCount;
    }

    //연관 관계 메서드
    private void foreignMember(Member newMember) {
        member = newMember;
        member.getOpinions().add(this);
    }

    private void foreignDetailNewArchive(NewsArchive newDetailNewsArchive) {
        newsArchive = newDetailNewsArchive;
        newsArchive.getOpinions().add(this);
    }

    private void eraseForeignKey() {
        member.getOpinions().remove(this);
        newsArchive.getOpinions().remove(this);
        member = null;
        newsArchive = null;
    }

    //비즈니스 로직
    public static Opinion createOpinion(Member newMember, NewsArchive newDetailNewsArchive, String newContent){
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
