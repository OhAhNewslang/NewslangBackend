package ohai.newslang.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class NewsArchive {

    @Id
    @GeneratedValue
    @Column(name = "news_archive_id")
    private Long id;

    @Embedded
    private News news;
}
