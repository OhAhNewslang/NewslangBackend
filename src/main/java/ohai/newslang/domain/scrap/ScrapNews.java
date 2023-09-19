package ohai.newslang.domain.scrap;

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
    private LocalDate scrapDate;

    @Builder
    public ScrapNews(String url, String mediaName, String writer, String title, String contents, LocalDate scrapDate) {
        this.url = url;
        this.mediaName = mediaName;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.scrapDate = scrapDate;
    }
}
