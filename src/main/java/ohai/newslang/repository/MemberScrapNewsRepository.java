package ohai.newslang.repository;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.MemberScrapNews;
import ohai.newslang.domain.subscribe.SubscribeKeyword;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberScrapNewsRepository {

    private final EntityManager em;

    public void save(MemberScrapNews memberScrapNews){
        em.persist(memberScrapNews);
    }

    public List<MemberScrapNews> findMemberScrapNewsWithMemberId(Long memberId){
        return em.createQuery("select msn from MemberScrapNews msn where msn.member.id = :memberId", MemberScrapNews.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
