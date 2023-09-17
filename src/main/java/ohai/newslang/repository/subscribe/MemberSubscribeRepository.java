package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberSubscribeRepository {

    private final EntityManager em;

    public void save(MemberSubscribe memberSubscribe){
        em.persist(memberSubscribe);
    }

    public void delete(MemberSubscribe memberSubscribe){
        em.remove(memberSubscribe);
    }

    public boolean isExistMemberSubscribe(Long memberId){
        Long result = (Long)em.createQuery("select count(ms.id) from MemberSubscribe ms where ms.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
        return ((result.equals(0L)) ? false : true);
    }

    public MemberSubscribe findOne(Long memberId){
        return em.createQuery("select ms from MemberSubscribe ms where ms.member.id = :memberId", MemberSubscribe.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
