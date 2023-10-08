package ohai.newslang.repository.recommand;

import ohai.newslang.domain.entity.recommend.MemberRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRecommendRepository extends JpaRepository<MemberRecommend, Long> {
    MemberRecommend findByMember_Id(Long id);
}
