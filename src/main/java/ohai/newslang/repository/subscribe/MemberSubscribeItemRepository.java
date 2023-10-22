package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

//@Repository
//@RequiredArgsConstructor
public interface MemberSubscribeItemRepository extends JpaRepository<MemberSubscribeItem, Long> {

//    private final EntityManager em;
//
//    public void save(MemberSubscribeItem memberSubscribeItem){
//        em.persist(memberSubscribeItem);
//    }


    @Query("select count(msi.id) from MemberSubscribeItem msi where msi.member.id = :memberId")
    Long countByMemberId(@Param("memberId") Long memberId);

//    @Query("select msi from MemberSubscribeItem msi where msi.member.id = :memberId")

    @Query("SELECT msi " +
            "FROM MemberSubscribeItem msi " +
            "JOIN msi.member m " +
            "JOIN FETCH msi.memberSubscribeMediaItemList msmi " +
            "WHERE m.id = :memberId")
    MemberSubscribeItem findByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT msi.id FROM MemberSubscribeItem msi " +
            "JOIN msi.member m " +
            "WHERE m.id = :memberId")
    Long findMemberSubscribeItemIdByMember_Id(@Param("memberId") Long memberId);



//    public boolean isExistMemberSubscribeItem(Long memberId){
//        Long result = (Long)em.createQuery("select count(msi.id) from MemberSubscribeItem msi where msi.member.id = :memberId")
//                .setParameter("memberId", memberId)
//                .getSingleResult();
//        return ((result.equals(0L)) ? false : true);
//    }

    //
//    public MemberSubscribeItem findOne(Long memberId){
//        return em.createQuery("select msi from MemberSubscribeItem msi where msi.member.id = :memberId", MemberSubscribeItem.class)
//                .setParameter("memberId", memberId)
//                .getSingleResult();
//    }
}