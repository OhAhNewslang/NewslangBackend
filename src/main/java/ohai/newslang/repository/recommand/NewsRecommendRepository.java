package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.DetailNewsRecommend;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsRecommendRepository extends JpaRepository<DetailNewsRecommend, Long> {
    @Query("SELECT dnr " +
            "FROM DetailNewsRecommend dnr " +
            "JOIN FETCH dnr.detailNewsArchive dna " +
            "WHERE dnr.memberRecommend.id = :memberRecommendId " +
            "AND dna.url = :newsUrl")
    Optional<DetailNewsRecommend> findByMemberRecommend_IdAndDetailNewsArchiveUrl(
                @Param("memberRecommendId") Long memberRecommendId,
                @Param("newsUrl") String newsUrl);
}
