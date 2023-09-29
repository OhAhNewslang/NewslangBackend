package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribeReference.Media;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
//Media
public class MediaRepository {

    private final EntityManager em;

    public void save(Media media){
        em.persist(media);
    }

    public Media findOne(Long id){
        return em.find(Media.class, id);
    }

    public List<Media> findAll(){
        return em.createQuery("select mi from Media mi", Media.class)
                .getResultList();
    }

    public List<Media> findAllWithNameList(List<String> nameList){
        return em.createQuery("select mi from Media mi" +
                        " where mi.name in :nameList", Media.class)
                .setParameter("nameList", nameList)
                .getResultList();
    }
}