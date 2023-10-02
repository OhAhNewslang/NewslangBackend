package ohai.newslang.repository.subscribe.subscribeReference;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Repository
//@RequiredArgsConstructor
//Media
public interface MediaRepository extends JpaRepository<Media, Long> {

//    private final EntityManager em;
//
//    public void save(Media media){
//        em.persist(media);
//    }
//
//    public Media findOne(Long id){
//        return em.find(Media.class, id);
//    }
//
//    public List<Media> findAll(){
//        return em.createQuery("select mi from Media mi", Media.class)
//                .getResultList();
//    }
//
//    public List<Media> findAllWithNameList(List<String> nameList){
//        return em.createQuery("select mi from Media mi" +
//                        " where mi.name in :nameList", Media.class)
//                .setParameter("nameList", nameList)
//                .getResultList();
//    }

    @Query("select count(m.id) from Media m where m.name = :name")
    Long countByName(@Param("name") String name);

    List<Media> findByNameIn(Collection<String> names);
}