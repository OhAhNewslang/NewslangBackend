package ohai.newslang.repository.subscribe;

import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeCategoryRepository extends JpaRepository<SubscribeCategory, Long> {

}