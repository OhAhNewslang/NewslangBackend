package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeMediaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface MemberSubscribeMediaItemRepository extends JpaRepository<MemberSubscribeMediaItem, Long> {

//    private final EntityManager em;
//
//    public void save(MemberSubscribeMediaItem memberSubscribeMediaItem){
//        em.persist(memberSubscribeMediaItem);
//    }
//
//    public List<MemberSubscribeMediaItem> findAllWithMemberSubscribeItemId(Long id){
//        return em.createQuery("select msmi from MemberSubscribeMediaItem msmi where msmi.memberSubscribeItem.id = :id", MemberSubscribeMediaItem.class)
//                .setParameter("id", id)
//                .getResultList();
//    }

    @Query("select msmi from MemberSubscribeMediaItem msmi where msmi.memberSubscribeItem.id = :memberSubscribeItemId")
    List<MemberSubscribeMediaItem> findByMemberSubscribeItemId(@Param("memberSubscribeItemId") Long memberSubscribeItemId);
}