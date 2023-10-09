package ohai.newslang.domain.entity.scrap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class ScrapNewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "scrap_news_archive_id")
    private Long id;

    @Column(nullable = false)
    private String newsUrl;

    @Column(nullable = false)
    private String mediaName;

    @Column(nullable = false)
    private String categoryName;

    @Column
    private String title;

    @Column
    private String contents;

    @Column
    private String thumbnailImagePath;

    @Column
    private LocalDate postDate;
}
