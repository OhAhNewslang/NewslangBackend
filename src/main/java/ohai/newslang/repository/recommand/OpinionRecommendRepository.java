package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpinionRecommendRepository extends JpaRepository<OpinionRecommend, Long> {
    // 현재 로그인한 멤버가 이 의견과 연관이 있는지 확인하기 위한 쿼리
//    @Query("SELECT or FROM OpinionRecommend or " +
//            "JOIN or.memberRecommend mr " +
//            "JOIN FETCH or.opinion o " +
//            "WHERE mr.id =:mRId AND o.id =:oId")
//    Optional<OpinionRecommend> findOpinionRecommend(@Param("mRId") Long mRId,
//                                              @Param("oId") Long oId);
    @EntityGraph(attributePaths = {"opinion"})
    Optional<OpinionRecommend> findByMemberRecommend_IdAndOpinion_Id(
            Long memberRecommendId,
            Long opinionId);
}
