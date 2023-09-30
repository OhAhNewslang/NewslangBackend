package ohai.newslang.domain.entity.news;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class NewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "news_archive_id")
    private Long id;

    @Embedded
    private News news;

    public NewsArchive(News news) {
        this.news = news;
    }
}
