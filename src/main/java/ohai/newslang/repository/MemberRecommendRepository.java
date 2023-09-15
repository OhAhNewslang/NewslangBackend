package ohai.newslang.repository;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.recommend.MemberRecommendComment;
import ohai.newslang.domain.recommend.MemberRecommendNews;
import ohai.newslang.domain.recommend.MemberRecommendOpinion;
import ohai.newslang.domain.subscribe.SubscribeCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRecommendRepository {

    private final EntityManager em;

    public void save(MemberRecommendNews memberRecommendNews){
        em.persist(memberRecommendNews);
    }

    public void save(MemberRecommendOpinion memberRecommendOpinion){
        em.persist(memberRecommendOpinion);
    }

    public void save(MemberRecommendComment memberRecommendComment){
        em.persist(memberRecommendComment);
    }

    public List<MemberRecommendNews> findMemberRecommendNewsWithMemberId(Long memberId){
        return em.createQuery("select mrn from MemberRecommendNews mrn where mrn.member.id = :memberId", MemberRecommendNews.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<MemberRecommendOpinion> findMemberRecommendOpinionWithMemberId(Long memberId){
        return em.createQuery("select mro from MemberRecommendOpinion mro where mro.member.id = :memberId", MemberRecommendOpinion.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<MemberRecommendComment> findMemberRecommendCommentWithMemberId(Long memberId){
        return em.createQuery("select mrc from MemberRecommendComment mrc where mrc.member.id = :memberId", MemberRecommendComment.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }
}
