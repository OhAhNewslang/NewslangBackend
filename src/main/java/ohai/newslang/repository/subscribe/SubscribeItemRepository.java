package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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

    public List<SubscribeItem> findAllIdWithNames(List<String> nameList, Class<?> entityType){
        return em.createQuery(
                        "select si from SubscribeItem si" +
                                " where type(si) = :entityType and si.name in :nameList", SubscribeItem.class)
                .setParameter("nameList", nameList)
                .setParameter("entityType", entityType)
                .getResultList();
    }

    public List<SubscribeItem> findAllWithMemberSubscribeItemId(Long memberSubscribeItemId, Class<?> entityType){
        return em.createQuery(
                        "select si from SubscribeItem si" +
                                " where si.memberSubscribeItem.id = :memberSubscribeItemId and type(si) = :entityType", SubscribeItem.class)
                .setParameter("memberSubscribeItemId", memberSubscribeItemId)
                .setParameter("entityType", entityType)
                .getResultList();
    }

    public List<SubscribeItem> findAllWithEntityType(Class<?> entityType){
        return em.createQuery(
                        "select si from SubscribeItem si" +
                                " where type(si) = :entityType", SubscribeItem.class)
                .setParameter("entityType", entityType)
                .getResultList();
    }

    public void deleteWithIds(List<Long> Ids){
        Query q = em.createQuery(
                        "delete from SubscribeItem si" +
                                " where si.id in :Ids")
                .setParameter("Ids", Ids);
        q.executeUpdate();
    }
}
