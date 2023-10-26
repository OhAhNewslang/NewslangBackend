package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.List;

public interface MemberScrapNewsArchiveRepository extends JpaRepository<MemberScrapNewsArchive, Long> {

    @Query("SELECT msna " +
            "FROM MemberScrapNewsArchive msna " +
            "JOIN FETCH msna.memberScrapNews msn " +
            "WHERE msn.member.id = :memberId")
    Page<MemberScrapNewsArchive> findByMemberId(@Param("memberId") Long memberId,
                                                         Pageable pageable);

    @Query("SELECT msna " +
            "FROM MemberScrapNewsArchive msna " +
            "JOIN FETCH msna.memberScrapNews msn " +
            "WHERE msn.member.id = :memberId")
    List<MemberScrapNewsArchive> findByMemberId(@Param("memberId") Long memberId);
}
