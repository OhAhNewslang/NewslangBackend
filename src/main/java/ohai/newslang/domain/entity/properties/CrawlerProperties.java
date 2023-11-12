package ohai.newslang.domain.entity.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CrawlerProperties {

    @Id
    @GeneratedValue
    @Column(name = "crawler_properties_id")
    private Long id;

    @Column
    private LocalDateTime crawlingDate;

    private int crawlingPeriodSecond;

    @Builder
    public CrawlerProperties(LocalDateTime crawlingDate, int crawlingPeriodSecond) {
        this.crawlingDate = crawlingDate;
        this.crawlingPeriodSecond = crawlingPeriodSecond;
    }
}
