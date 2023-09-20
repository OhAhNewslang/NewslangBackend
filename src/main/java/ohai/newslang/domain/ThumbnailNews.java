package ohai.newslang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ThumbnailNews {
    private String link;
    private String title;
    private String summary;
    private String imagePath;
    private String mediaName;

    @Builder
    public ThumbnailNews(String link, String title, String summary, String imagePath, String mediaName) {
        this.link = link;
        this.title = title;
        this.summary = summary;
        this.imagePath = imagePath;
        this.mediaName = mediaName;
    }
}
