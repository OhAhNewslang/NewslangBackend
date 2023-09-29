package ohai.newslang.domain.entity.scrap;

import lombok.Getter;
import lombok.Setter;
import ohai.newslang.domain.entity.news.NewsArchive;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class MemberScrapNewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "scrap_news_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_scrap_news_id")
    private MemberScrapNews memberScrapNews;

    @ManyToOne
    @JoinColumn(name = "news_archive_id")
    private NewsArchive newsArchive;

    @Column(nullable = false)
    private LocalDate scrapDate;
}
