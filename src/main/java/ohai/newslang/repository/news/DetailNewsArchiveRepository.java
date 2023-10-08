package ohai.newslang.repository.news;

import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.news.DetailNewArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailNewsArchiveRepository extends JpaRepository<DetailNewArchive, Long> {
    @Query("SELECT dna FROM DetailNewArchive dna WHERE dna.id =:dnaId")
    DetailNewArchive findNoOptionalById(@Param("dnaId") Long dnaId);

}
