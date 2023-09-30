package ohai.newslang.repository.member;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.member.Member;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public Member findByUserName(String name){
        return em.createQuery("select m from Member m" +
                        " where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public Optional<Member> findById(Long id) {
        Member findMember = em.createQuery("select m from Member m" +
                        " where m.id = :id", Member.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(findMember);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
