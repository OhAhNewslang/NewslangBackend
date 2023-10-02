package ohai.newslang.repository.member;

import ohai.newslang.domain.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByLoginId(String loginId);
    @Query("SELECT m FROM Member m WHERE m.id =:id")
    Member findByTokenId(@Param("id") Long id);

}
