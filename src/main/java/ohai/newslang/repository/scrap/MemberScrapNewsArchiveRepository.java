package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    @Query("select msna from MemberScrapNewsArchive msna where msna.memberScrapNews.id = :memberScrapNewsId")
//    Page<MemberScrapNewsArchive> findByMemberScrapNewsId(@Param("memberScrapNewsId") Long memberScrapNewsId,
//                                                         Pageable pageable);


    @Query("SELECT msna " +
            "FROM MemberScrapNewsArchive msna " +
            "JOIN msna.memberScrapNews msn " +
            "JOIN FETCH msna.newsArchive na " +
            "WHERE msn.member.id = :memberId")
    Page<MemberScrapNewsArchive> findByMemberId(@Param("memberId") Long memberId,
                                                         Pageable pageable);

    @Query("SELECT msna " +
            "FROM MemberScrapNewsArchive msna " +
            "JOIN msna.memberScrapNews msn " +
            "JOIN FETCH msna.newsArchive na " +
            "WHERE msn.member.id = :memberId")
    List<MemberScrapNewsArchive> findByMemberId(@Param("memberId") Long memberId);

//    public void delete(Long memberScrapNewsId, String url){
//        Query q = em.createQuery(
//                        "delete from MemberScrapNewsArchive msna"+
//                                " where msna.memberScrapNews.id = :memberScrapNewsId and msna.newsArchive.news.url = :url")
//                .setParameter("memberScrapNewsId", memberScrapNewsId)
//                .setParameter("url", url);
//        q.executeUpdate();
//    }
}
