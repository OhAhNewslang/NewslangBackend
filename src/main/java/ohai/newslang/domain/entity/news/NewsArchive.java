package ohai.newslang.domain.entity.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class NewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "news_archive_id")
    private Long id;
    @Column
    private String url;
    @Column
    private String mediaName;
    @Column
    private String category;
    @Column
    private String title;
    @Column(length = Integer.MAX_VALUE)
    private String contents;
    @Column
    private String imagePath;
    @Column
    private LocalDateTime postDateTime;
    @Column
    private LocalDateTime modifyDateTime;

    @Builder
    public NewsArchive(Long id, String url, String mediaName, String category, String title, String contents, String imagePath, LocalDateTime postDateTime, LocalDateTime modifyDateTime) {
        this.id = id;
        this.url = url;
        this.mediaName = mediaName;
        this.category = category;
        this.title = title;
        this.contents = contents;
        this.imagePath = imagePath;
        this.postDateTime = postDateTime;
        this.modifyDateTime = modifyDateTime;
    }
}
