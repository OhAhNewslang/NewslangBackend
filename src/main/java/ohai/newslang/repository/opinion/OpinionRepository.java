package ohai.newslang.repository.opinion;

import ohai.newslang.domain.entity.opinion.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    @Query("SELECT o FROM Opinion o WHERE o.id =:oId")
    Opinion findNoOptionalById(@Param("oId") Long oId);

    // 마이페이지의 내 의견 목록 DTO로 조회
    @Query("SELECT o FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "JOIN FETCH o.opinionRecommends or " +
            "WHERE o.member.id =:id")
    List<Opinion> findOpinionDtosByMemberId(@Param("id") Long id);

    // 상세 뉴스의 전체 의견 목록 DTO로 조회
    @Query("SELECT o FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "JOIN FETCH o.opinionRecommends or " +
            "WHERE o.detailNewsArchive.id =:id")
    List<Opinion> findOpinionDtosByDetailNewArchiveId(@Param("id") Long id);
}
