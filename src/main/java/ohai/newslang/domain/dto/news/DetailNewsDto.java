package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.enumulate.RecommendStatus;

import java.time.LocalDateTime;

@Getter
public class DetailNewsDto {
    private String url;
    private String title;
    private String contents;
    private String media;
    private int likeCount;
    private LocalDateTime postDateTime;
    private LocalDateTime modifyDateTime;
    private String reporter;

    @Builder
    public DetailNewsDto(String url, String title, String contents, String media, int likeCount, LocalDateTime postDateTime, LocalDateTime modifyDateTime, String reporter) {
        this.url = url;
        this.title = title;
        this.contents = contents;
        this.media = media;
        this.likeCount = likeCount;
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
        this.reporter = reporter;
    }
}
