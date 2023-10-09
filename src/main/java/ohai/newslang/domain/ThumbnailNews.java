package ohai.newslang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ThumbnailNews {
    private String link;
    private String title;
    private String summary;
    private String contents;
    private String imagePath;
    private String media;
    private String category;
    private LocalDateTime postDateTime;

    @Builder
    public ThumbnailNews(String link, String title, String summary, String contents, String imagePath, String media, String category, LocalDateTime postDateTime) {
        this.link = link;
        this.title = title;
        this.summary = summary;
        this.contents = contents;
        this.imagePath = imagePath;
        this.media = media;
        this.category = category;
        this.postDateTime = postDateTime;
    }
}
