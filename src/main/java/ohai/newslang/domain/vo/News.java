package ohai.newslang.domain.vo;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class News {
    private String url;
    private String title;
    private String summary;
    private String contents;
    private String imagePath;
    private String media;
    private String oId;
    private LocalDateTime postDateTime;
    private LocalDateTime modifyDateTime;
    private String reporter;

    @Builder
    public News(String url, String title, String summary, String contents, String imagePath, String media, String oId, LocalDateTime postDateTime, LocalDateTime modifyDateTime, String reporter) {
        this.url = url;
        this.title = title;
        this.summary = summary;
        this.contents = contents;
        this.imagePath = imagePath;
        this.media = media;
        this.oId = oId;
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
        this.reporter = reporter;
    }
}
