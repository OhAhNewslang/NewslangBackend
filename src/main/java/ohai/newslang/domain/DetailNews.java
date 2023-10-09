package ohai.newslang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DetailNews {
    private String link;
    private String title;
    private String contents;
    private String media;
//    private String category;
    private LocalDateTime postDateTime;
    private LocalDateTime modifyDateTime;

    @Builder
    public DetailNews(String link, String title, String contents, String media, LocalDateTime postDateTime, LocalDateTime modifyDateTime) {
        this.link = link;
        this.title = title;
        this.contents = contents;
        this.media = media;
//        this.category = category;
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
    }
}
