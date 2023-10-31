package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.NewsRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsRecommendRepository extends JpaRepository<NewsRecommend, Long> {
    @Query("SELECT nr " +
            "FROM NewsRecommend nr " +
            "JOIN FETCH nr.newsArchive na " +
            "WHERE nr.memberRecommend.id = :memberRecommendId " +
            "AND na.url = :newsUrl")
    Optional<NewsRecommend> findByMemberRecommend_IdAndDetailNewsArchiveUrl(
                @Param("memberRecommendId") Long memberRecommendId,
                @Param("newsUrl") String newsUrl);

    Optional<NewsRecommend> findByNewsArchive_Url(@Param("newsUrl") String newsUrl);

}
