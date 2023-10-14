package ohai.newslang.repository.opinion;

import ohai.newslang.domain.entity.opinion.Opinion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    Opinion findNoOptionalById(Long oId);
    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "WHERE o.id = :oId")
    Opinion findNoOptionalJoinMemberById(@Param("oId") Long oId);

    // member를 Fetch Join해서 Slice<Opinion>으로 페이징
    // 마이페이지용 memberId key값으로 조회
    @EntityGraph(attributePaths = {"member"})
    Slice<Opinion> findAllByMemberId(Long id, Pageable pageable);

    // 상세 뉴스의 전체 의견 목록 DTO로 조회
    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "JOIN o.detailNewsArchive dna " +
            "WHERE dna.url = :newUrl")
    Slice<Opinion> findAllByDetailNewsArchiveUrl(@Param("newUrl") String newUrl,
                                                 Pageable pageable);
}
