package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.MemberSubscribeItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberSubscribeItemRepository {

    private final EntityManager em;

    public void save(MemberSubscribeItem memberSubscribeItem){
        em.persist(memberSubscribeItem);
    }

    public boolean isExistMemberSubscribeItem(Long memberId){
        Long result = (Long)em.createQuery("select count(msi.id) from MemberSubscribeItem msi where msi.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
        return ((result.equals(0L)) ? false : true);
    }
    //
    public MemberSubscribeItem findOne(Long memberId){
        return em.createQuery("select msi from MemberSubscribeItem msi where msi.member.id = :memberId", MemberSubscribeItem.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}