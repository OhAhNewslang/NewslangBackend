package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.SubscribeCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscribeCategoryRepository {

    private final EntityManager em;

    public void save(SubscribeCategory subscribeCategory){
        em.persist(subscribeCategory);
    }

    public List<SubscribeCategory> findAll(){
        return em.createQuery("select c from SubscribeCategory c", SubscribeCategory.class)
                .getResultList();
    }
}
