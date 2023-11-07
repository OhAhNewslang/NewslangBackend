package ohai.newslang.repository.opinion;

import ohai.newslang.domain.entity.opinion.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    Opinion findNoOptionalByUuid(String uuid);
    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "WHERE o.uuid = :uuid")
    Opinion findNoOptionalJoinMemberByUuid(@Param("uuid") String uuid);

    // member를 Fetch Join해서 Slice<Opinion>으로 페이징
    // 마이페이지용 memberId key값으로 조회
    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "WHERE m.id = :mid")
    Page<Opinion> findAllByMemberId(@Param("mid") Long mid, Pageable pageable);

    // 상세 뉴스의 전체 의견 목록 DTO로 조회
    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "JOIN o.newsArchive dna " +
            "WHERE dna.url = :newUrl")
    Page<Opinion> findAllByDetailNewsArchiveUrl(@Param("newUrl") String newUrl,
                                                Pageable pageable);

    @Query("SELECT o " +
            "FROM Opinion o " +
            "JOIN FETCH o.member m " +
            "JOIN o.newsArchive dna " +
            "WHERE dna.url = :newUrl")
    List<Opinion> findAllByDetailNewsArchiveUrl(@Param("newUrl") String newUrl);
}
