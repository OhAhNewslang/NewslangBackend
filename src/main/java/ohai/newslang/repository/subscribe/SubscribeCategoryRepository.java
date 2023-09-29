package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.SubscribeCategory;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
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