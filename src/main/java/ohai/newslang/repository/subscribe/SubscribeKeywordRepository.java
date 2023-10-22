package ohai.newslang.repository.subscribe;

import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeKeywordRepository extends JpaRepository<SubscribeKeyword, Long> {

    List<SubscribeKeyword> findAllByMemberSubscribeItem_Id(Long subscribeId);
}
