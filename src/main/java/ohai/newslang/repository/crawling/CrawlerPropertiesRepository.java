package ohai.newslang.repository.crawling;

import ohai.newslang.domain.entity.CrawlerProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlerPropertiesRepository extends JpaRepository<CrawlerProperties, Long> {
}
