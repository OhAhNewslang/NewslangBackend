package ohai.newslang.repository;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.CrawlingInfo;
import ohai.newslang.domain.MemberScrapNews;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CrawlingInfoRepository {

    private final EntityManager em;

    public void save(CrawlingInfo crawlingInfo){
        em.persist(crawlingInfo);
    }

    public List<CrawlingInfo> findCrawlingInfoWithMemberId(Long memberId){
        return em.createQuery("select ci from CrawlingInfo ci where ci.member.id = :memberId", CrawlingInfo.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
