package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.reference.Media;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
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
