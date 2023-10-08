package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.NewsRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NewsRecommendRepository extends JpaRepository<NewsRecommend, Long> {
    // 특정 뉴스의 추천 개수 구하는 쿼리
    Long countAllByDetailNewArchive_IdAndStatus(Long id,RecommendStatus status);

    // 현재 로그인한 멤버가 이 상세 뉴스와 연관이 있는지 확인하기 위한 쿼리
    @Query("SELECT nr FROM NewsRecommend nr " +
            "JOIN Member m " +
            "JOIN DetailNewArchive dna " +
            "WHERE m.id =:mId AND dna.id=: dNAId")
    Optional<NewsRecommend> findNewsRecommend(@Param("mId") Long mId,
                                              @Param("dNAId") Long dNAId);
}
