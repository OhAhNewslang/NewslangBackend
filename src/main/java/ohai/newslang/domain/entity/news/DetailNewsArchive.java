package ohai.newslang.domain.entity.news;

import jakarta.persistence.*;
import lombok.*;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.DetailNewsRecommend;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailNewsArchive extends TimeStamp {

    @Id @GeneratedValue
    @Column(name = "detail_news_archive_id")
    private Long id;

    @Column(nullable = false)
    private String newsUrl;

    private int likeCount;

    @OneToMany(mappedBy = "detailNewsArchive", cascade = CascadeType.ALL)
    private List<DetailNewsRecommend> detailNewsRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "detailNewsArchive", cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    //생성자
    @Builder
    public DetailNewsArchive(String newsUrl) {
        this.newsUrl = newsUrl;
        this.likeCount = 0;
    }

    //연관 관계 메서드

    //비즈니스 로직
    public void updateLikeCount(int count) {
        likeCount += count;
        checkCount(likeCount);
    }

    // 추천 수가 0 밑으로 내려가면 0으로 초기화
    private void checkCount(int newCount) {
        likeCount = Math.max(newCount, 0);
    }
}
