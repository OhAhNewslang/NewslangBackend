package ohai.newslang.repository.subscribe;

import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberSubscribeMediaItemRepository extends JpaRepository<MemberSubscribeMediaItem, Long> {

}