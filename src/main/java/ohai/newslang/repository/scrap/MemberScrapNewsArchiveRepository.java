package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.scrap.MemberScrapNewsArchive;
import ohai.newslang.domain.subscribe.MemberSubscribeItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberScrapNewsArchiveRepository {

    private final EntityManager em;

    public List<MemberScrapNewsArchive> findAllWithMemberScrapNewsId(Long memberScrapNewsId){
        return em.createQuery("select msna from MemberScrapNewsArchive msna" +
                        " where msna.memberScrapNews.id = :memberScrapNewsId")
                .setParameter("memberScrapNewsId", memberScrapNewsId)
                .getResultList();
    }

    public void delete(Long memberScrapNewsId, List<String> urls){
        Query q = em.createQuery(
                        "delete from MemberScrapNewsArchive msna"+
                                " where msna.memberScrapNews.id = :memberScrapNewsId and msna.newsArchive.news.url in :urls")
                .setParameter("memberScrapNewsId", memberScrapNewsId)
                .setParameter("urls", urls);
        q.executeUpdate();
    }

    public void delete(Long memberScrapNewsId, String url){
        Query q = em.createQuery(
                        "delete from MemberScrapNewsArchive msna"+
                                " where msna.memberScrapNews.id = :memberScrapNewsId and msna.newsArchive.news.url = :url")
                .setParameter("memberScrapNewsId", memberScrapNewsId)
                .setParameter("url", url);
        q.executeUpdate();
    }
}
