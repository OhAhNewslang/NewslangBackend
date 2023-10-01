package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface SubscribeCategoryRepository extends JpaRepository<SubscribeCategory, Long> {

//    private final EntityManager em;
//
//    public void save(SubscribeCategory subscribeCategory){
//        em.persist(subscribeCategory);
//    }
//
//    public List<SubscribeCategory> findAll(){
//        return em.createQuery("select c from SubscribeCategory c", SubscribeCategory.class)
//                .getResultList();
//    }
}