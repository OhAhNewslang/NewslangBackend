package ohai.newslang.domain.entity.news;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @OneToMany(mappedBy = "detailNewsArchive", cascade = CascadeType.ALL)
    private List<DetailNewsRecommend> detailNewsRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "detailNewsArchive", cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    //연관 관계 메서드


    //비즈니스 로직
}
