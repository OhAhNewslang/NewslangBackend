package ohai.newslang.domain.dto.scrap;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ScrapNews {
    private String url;
    private String mediaName;
    private String writer;
    private String title;
    private String contents;
    private String thumbnailImagePath;
    private LocalDate scrapDate;

    @Builder
    public ScrapNews(String url, String mediaName, String writer, String title, String contents, String thumbnailImagePath, LocalDate scrapDate) {
        this.url = url;
        this.mediaName = mediaName;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.thumbnailImagePath = thumbnailImagePath;
        this.scrapDate = scrapDate;
    }
}
