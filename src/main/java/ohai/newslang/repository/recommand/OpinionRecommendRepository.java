package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OpinionRecommendRepository extends JpaRepository<OpinionRecommend, Long> {
    // 특정 의견의 추천, 비 추천 개수 구하는 쿼리
    Long countAllByOpinion_IdAndStatus(Long id, RecommendStatus status);

    // 현재 로그인한 멤버가 이 의견과 연관이 있는지 확인하기 위한 쿼리
    @Query("SELECT ord FROM OpinionRecommend ord " +
            "JOIN Member m " +
            "JOIN Opinion o " +
            "WHERE m.id =:mId AND o.id =:oId")
    Optional<OpinionRecommend> findOpinionRecommend(@Param("mId") Long mRId,
                                              @Param("oId") Long oId);
}
