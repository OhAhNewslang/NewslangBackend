package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSubscribeItemRepository {

    private final EntityManager em;

    public List<MemberSubscribeItem> findAllWithMemberSubscribeId(Long memberSubscribeId, List<Long> subscribeItemIds){
        return em.createQuery("select msi from MemberSubscribeItem msi" +
                " where msi.memberSubscribe.id = :memberSubscribeId and msi.subscribeItem.id in :subscribeItemIds")
                .setParameter("memberSubscribeId", memberSubscribeId)
                .setParameter("subscribeItemIds", subscribeItemIds)
                .getResultList();
    }

    public void delete(Long memberSubscribeId, List<Long> subscribeItemIds){
        Query q = em.createQuery(
                "delete from MemberSubscribeItem msi"+
                " where msi.memberSubscribe.id = :subscribeMemberId and msi.subscribeItem.id in :subscribeItemIds")
                .setParameter("subscribeMemberId", memberSubscribeId)
                .setParameter("subscribeItemIds", subscribeItemIds);
        q.executeUpdate();
    }
}
