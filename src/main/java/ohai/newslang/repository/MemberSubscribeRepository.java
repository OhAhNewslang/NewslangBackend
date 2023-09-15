package ohai.newslang.repository;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.Media;
import ohai.newslang.domain.subscribe.SubscribeCategory;
import ohai.newslang.domain.subscribe.SubscribeKeyword;
import ohai.newslang.domain.subscribe.SubscribeMedia;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberSubscribeRepository {

    private final EntityManager em;

    public void save(SubscribeCategory subscribeCategory){
        em.persist(subscribeCategory);
    }

    public void save(SubscribeKeyword subscribeKeyword){
        em.persist(subscribeKeyword);
    }

    public void save(SubscribeMedia subscribeMedia){
        em.persist(subscribeMedia);
    }

    public List<SubscribeCategory> findSubscribeCategoryWithMemberId(Long memberId){
        return em.createQuery("select sc from SubscribeCategory sc where sc.member.id = :memberId", SubscribeCategory.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<SubscribeKeyword> findSubscribeKeywordWithMemberId(Long memberId){
        return em.createQuery("select sk from SubscribeKeyword sk where sk.member.id = :memberId", SubscribeKeyword.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<SubscribeMedia> findSubscribeMediaWithMemberId(Long memberId){
        return em.createQuery("select sm from SubscribeMedia sm where sm.member.id = :memberId", SubscribeMedia.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public void updateMedias(Long memberId, List<Media> mediaList){
//        em.createQuery(
//                "select sm from SubscribeMedia sm" +
//                " where sm.member.id = :memberId" +
//                " set ")
    }
}
