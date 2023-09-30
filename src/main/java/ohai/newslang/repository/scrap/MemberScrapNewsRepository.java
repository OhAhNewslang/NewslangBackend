package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberScrapNewsRepository {

    private final EntityManager em;

    public void save(MemberScrapNews memberScrapNews){
        em.persist(memberScrapNews);
    }

    public void delete(MemberScrapNews memberScrapNews){
        em.remove(memberScrapNews);
    }

    public boolean isExistMemberScrapNews(Long memberId){
        Long result = (Long)em.createQuery("select count(msn.id) from MemberScrapNews msn where msn.member.id = :memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
        return (!result.equals(0L));
    }

    public MemberScrapNews findOne(Long memberId){
        return em.createQuery("select msn from MemberScrapNews msn where msn.member.id = :memberId", MemberScrapNews.class)
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
