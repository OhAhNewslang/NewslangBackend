package ohai.newslang.domain.dto.news;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ThumbnailNewsDto {
    private String url;
    private String media;
    private String category;
    private String title;
    private String summary;
    private String imagePath;
    private LocalDateTime postDateTime;

    @Builder
    public ThumbnailNewsDto(String url, String media, String category, String title, String summary, String imagePath, LocalDateTime postDateTime) {
        this.url = url;
        this.media = media;
        this.category = category;
        this.title = title;
        this.summary = summary;
        this.imagePath = imagePath;
        this.postDateTime = postDateTime;
    }
}