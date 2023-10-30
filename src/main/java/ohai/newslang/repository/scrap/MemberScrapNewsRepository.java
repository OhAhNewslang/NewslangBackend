package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public interface MemberScrapNewsRepository extends JpaRepository<MemberScrapNews, Long> {

    @Query("select count(msn.id) from MemberScrapNews msn where msn.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);


    @Query("select msn from MemberScrapNews msn where msn.member.id = :memberId")
    Optional<MemberScrapNews> findByMemberId(@Param("memberId") Long memberId);
}
