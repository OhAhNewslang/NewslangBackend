package ohai.newslang.repository.properties;

import ohai.newslang.domain.entity.properties.CrawlerProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlerPropertiesRepository extends JpaRepository<CrawlerProperties, Long> {
}
