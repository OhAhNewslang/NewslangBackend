package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public interface MemberSubscribeItemRepository extends JpaRepository<MemberSubscribeItem, Long> {

    Optional<MemberSubscribeItem> findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT msi " +
            "FROM MemberSubscribeItem msi " +
            "JOIN FETCH msi.memberSubscribeMediaItemList sm " +
            "JOIN FETCH sm.media m " +
            "WHERE msi.member.id = :memberId")
    Optional<MemberSubscribeItem> findSubscribeMediaByMemberId(@Param("memberId") Long memberId);
}