package ohai.newslang.repository.scrap;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.scrap.MemberScrapNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

//@Repository
//@RequiredArgsConstructor
public interface MemberScrapNewsRepository extends JpaRepository<MemberScrapNews, Long> {

//    private final EntityManager em;
//
//    public void save(MemberScrapNews memberScrapNews){
//        em.persist(memberScrapNews);
//    }
//
//    public void delete(MemberScrapNews memberScrapNews){
//        em.remove(memberScrapNews);
//    }


    @Query("select count(msn.id) from MemberScrapNews msn where msn.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);


    @Query("select msn from MemberScrapNews msn where msn.member.id = :memberId")
    MemberScrapNews findByMemberId(@Param("memberId") Long memberId);

//    public boolean isExistMemberScrapNews(Long memberId){
//        Long result = (Long)em.createQuery("select count(msn.id) from MemberScrapNews msn where msn.member.id = :memberId")
//                .setParameter("memberId", memberId)
//                .getSingleResult();
//        return (!result.equals(0L));
//    }

//    public MemberScrapNews findOne(Long memberId){
//        return em.createQuery("select msn from MemberScrapNews msn where msn.member.id = :memberId", MemberScrapNews.class)
//                .setParameter("memberId", memberId)
//                .getSingleResult();
//    }
}
