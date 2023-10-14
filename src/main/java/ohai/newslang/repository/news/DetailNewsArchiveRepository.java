package ohai.newslang.repository.news;

import ohai.newslang.domain.entity.news.DetailNewsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailNewsArchiveRepository extends JpaRepository<DetailNewsArchive, Long> {
    @Query("SELECT dna FROM DetailNewsArchive dna WHERE dna.url =:newsUrl")
    DetailNewsArchive findNoOptionalByNewsUrl(@Param("newsUrl") String newsUrl);

}
