package ohai.newslang.domain.dto.scrap;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScrapNewsDto {
    private String newsUrl;
    private String mediaName;
    private String category;
    private String title;
    private String imagePath;
    private String postDateTime;
    private String scrapDateTime;

    @Builder
    public ScrapNewsDto(String newsUrl, String mediaName, String category, String title, String imagePath, String postDateTime, String scrapDateTime) {
        this.newsUrl = newsUrl;
        this.mediaName = mediaName;
        this.category = category;
        this.title = title;
        this.imagePath = imagePath;
        this.postDateTime = postDateTime;
        this.scrapDateTime = scrapDateTime;
    }
}
