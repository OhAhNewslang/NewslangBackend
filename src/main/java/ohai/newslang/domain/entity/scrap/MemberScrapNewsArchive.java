package ohai.newslang.domain.entity.scrap;

import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name = "scrap_news_archive_id")
    private ScrapNewsArchive scrapNewsArchive;

    @Column(nullable = false)
    private LocalDate scrapDate;
}
