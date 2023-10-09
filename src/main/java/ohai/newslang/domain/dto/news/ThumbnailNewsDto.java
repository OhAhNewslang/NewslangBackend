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
    private boolean isSubscribeNews;

    @Builder
    public ThumbnailNewsDto(String url, String media, String category, String title, String summary, String imagePath, LocalDateTime postDateTime, boolean isSubscribeNews) {
        this.url = url;
        this.media = media;
        this.category = category;
        this.title = title;
        this.summary = summary;
        this.imagePath = imagePath;
        this.postDateTime = postDateTime;
        this.isSubscribeNews = isSubscribeNews;
    }
}