package ohai.newslang.domain.entity.news;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.entity.TimeStamp;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DetailNewArchive extends TimeStamp {

    @Id @GeneratedValue
    @Column(name = "detail_news_archive_id")
    private Long id;

    @Column(nullable = false)
    private String newsUrl;

    @OneToMany(mappedBy = "detail_news_archive", cascade = CascadeType.ALL)
    @JoinColumn(name = "news_recommend_id")
    private List<NewsRecommend> newsRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "detail_news_archive", cascade = CascadeType.ALL)
    @JoinColumn(name = "opinion_id")
    private List<Opinion> opinions = new ArrayList<>();

    //연관 관계 메서드


    //비즈니스 로직
}
