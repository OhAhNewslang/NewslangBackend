package ohai.newslang.repository.opinion;

import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.domain.entity.opinion.Opinion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {

    Opinion findNoOptionalById(Long oId);

    // member를 Fetch Join한 Slice로 페이징되는 Opinion

    // 마이페이지용 memberId key값으로 조회
    @EntityGraph(attributePaths = {"member"})
    Slice<Opinion> findAllByMemberId(Long id, Pageable pageable);

    // 상세 뉴스의 전체 의견 목록 DTO로 조회
    @EntityGraph(attributePaths = {"member"})
    Slice<Opinion> findAllByDetailNewsArchive_Id(Long id, Pageable pageable);
}
