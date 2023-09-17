package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class SubscribeItemRepository {

    private final EntityManager em;

    public void save(SubscribeItem item){
        em.persist(item);
    }

    public SubscribeItem findOne(Long id){
        return em.find(SubscribeItem.class, id);
    }

    public SubscribeItem findByName(String name){
        return em.createQuery(
                "select si from SubscribeItem si" +
                        " where si.name = :name", SubscribeItem.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public List<SubscribeItem> findAllIdWithName(List<String> nameList, Class<?> entityType){
        return em.createQuery(
                        "select si from SubscribeItem si" +
                                " where type(si) = :entityType and si.name in :nameList", SubscribeItem.class)
                .setParameter("nameList", nameList)
                .setParameter("entityType", entityType)
                .getResultList();
    }

    public List<SubscribeItem> findAll(Class<?> entityType){
        return em.createQuery(
                        "select si from SubscribeItem si" +
                                " where type(si) = :entityType", SubscribeItem.class)
                .setParameter("entityType", entityType)
                .getResultList();
    }
}
