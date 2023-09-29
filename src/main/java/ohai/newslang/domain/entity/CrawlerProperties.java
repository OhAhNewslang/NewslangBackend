package ohai.newslang.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class CrawlerProperties {

    @Id
    @GeneratedValue
    @Column(name = "crawler_properties_id")
    private Long id;

    private LocalDateTime crawlingDate;

    private int crawlingPeriodSecond;

    @Builder
    public CrawlerProperties(LocalDateTime crawlingDate, int crawlingPeriodSecond) {
        this.crawlingDate = crawlingDate;
        this.crawlingPeriodSecond = crawlingPeriodSecond;
    }
}
