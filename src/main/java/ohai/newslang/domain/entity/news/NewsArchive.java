package ohai.newslang.domain.entity.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import ohai.newslang.domain.entity.opinion.Opinion;
import ohai.newslang.domain.entity.recommend.NewsRecommend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class NewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "news_archive_id")
    private Long id;

    @Column
    private String url;

    @Column
    private String mediaName;

    @Column
    private String category;

    @Column
    private String title;

    @Column(length = Integer.MAX_VALUE)
    private String contents;

    @Column
    private String imagePath;

    @Column
    private int likeCount;

    @Column
    private LocalDateTime postDateTime;

    @Column
    private LocalDateTime modifyDateTime;

    @Column
    private String reporter;

    @OneToMany(mappedBy = "newsArchive", cascade = CascadeType.ALL)
    private List<NewsRecommend> newsRecommends = new ArrayList<>();

    @OneToMany(mappedBy = "newsArchive", cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    @Builder
    public NewsArchive(Long id, String url, String mediaName, String category, String title, String contents, String imagePath, Long countLike, LocalDateTime postDateTime, LocalDateTime modifyDateTime, String reporter) {
        this.id = id;
        this.url = url;
        this.mediaName = mediaName;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.imagePath = imagePath;
        this.likeCount = 0;
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
        this.reporter = reporter;
    }

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
