package ohai.newslang.domain.entity.scrap;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import ohai.newslang.domain.entity.news.NewsArchive;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class MemberScrapNewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "member_scrap_news_archive_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_scrap_news_id")
    private MemberScrapNews memberScrapNews;

    @ManyToOne
    @JoinColumn(name = "news_archive_id")
    private NewsArchive newsArchive;

    @Column(nullable = false)
    private LocalDateTime scrapDateTime;

    public static MemberScrapNewsArchive createMemberScrapNews(NewsArchive newsArchive){
        MemberScrapNewsArchive memberScrapNewsArchive = new MemberScrapNewsArchive();
        memberScrapNewsArchive.setNewsArchive(newsArchive);
        memberScrapNewsArchive.setScrapDateTime(LocalDateTime.now());
        return memberScrapNewsArchive;
    }
}
