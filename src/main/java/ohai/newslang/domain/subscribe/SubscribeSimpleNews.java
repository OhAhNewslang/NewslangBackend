package ohai.newslang.domain.subscribe;


import lombok.Builder;
import lombok.Getter;

@Getter
public class SubscribeSimpleNews {
    private String url;
    private String mediaName;
    private String categoryName;
    private String title;
    private String contents;

    @Builder
    public SubscribeSimpleNews(String url, String mediaName, String categoryName, String title, String contents) {
        this.url = url;
        this.mediaName = mediaName;
        this.categoryName = categoryName;
        this.title = title;
        this.contents = contents;
    }
}