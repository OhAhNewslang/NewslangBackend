package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface MemberScrapNewsArchiveRepository extends JpaRepository<MemberScrapNewsArchive, Long> {

//    private final EntityManager em;
//
//    public List<MemberScrapNewsArchive> findAllWithMemberScrapNewsId(Long memberScrapNewsId){
//        return em.createQuery("select msna from MemberScrapNewsArchive msna" +
//                        " where msna.memberScrapNews.id = :memberScrapNewsId",MemberScrapNewsArchive.class)
//                .setParameter("memberScrapNewsId", memberScrapNewsId)
//                .getResultList();
//    }

    @Query("select msna from MemberScrapNewsArchive msna where msna.memberScrapNews.id = :memberScrapNewsId")
    List<MemberScrapNewsArchive> findByMemberScrapNewsId(@Param("memberScrapNewsId") Long memberScrapNewsId);

    @Query("select msna.id from MemberScrapNewsArchive msna where msna.memberScrapNews.member.id = :memberScrapNewsId and msna.newsArchive.news.url = :url")
    Long findById(@Param("memberScrapNewsId") Long memberScrapNewsId,
                  @Param("url") String url);

    @Query("delete from MemberScrapNewsArchive msna where msna.memberScrapNews.member.id = :memberScrapNewsId and msna.newsArchive.news.url = :url")
    Long deleteByMemberScrapNewsIdAndUrl(@Param("memberScrapNewsId") Long memberScrapNewsId,
                                         @Param("url") String url);

//    public void delete(Long memberScrapNewsId, String url){
//        Query q = em.createQuery(
//                        "delete from MemberScrapNewsArchive msna"+
//                                " where msna.memberScrapNews.id = :memberScrapNewsId and msna.newsArchive.news.url = :url")
//                .setParameter("memberScrapNewsId", memberScrapNewsId)
//                .setParameter("url", url);
//        q.executeUpdate();
//    }
}
