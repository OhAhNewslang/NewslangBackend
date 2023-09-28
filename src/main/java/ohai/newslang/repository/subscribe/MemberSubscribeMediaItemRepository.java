package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.subscribe.MemberSubscribeMediaItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSubscribeMediaItemRepository {

    private final EntityManager em;

    public void save(MemberSubscribeMediaItem memberSubscribeMediaItem){
        em.persist(memberSubscribeMediaItem);
    }

    public List<MemberSubscribeMediaItem> findAllWithMemberSubscribeItemId(Long id){
        return em.createQuery("select msmi from MemberSubscribeMediaItem msmi where msmi.memberSubscribeItem.id = :id", MemberSubscribeMediaItem.class)
                .setParameter("id", id)
                .getResultList();
    }
}