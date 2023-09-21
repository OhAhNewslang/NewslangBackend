package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.MediaItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MediaItemRepository {

    private final EntityManager em;

    public void save(MediaItem mediaItem){
        em.persist(mediaItem);
    }

    public MediaItem findOne(Long id){
        return em.find(MediaItem.class, id);
    }

    public List<MediaItem> findAll(){
        return em.createQuery("select mi from MediaItem mi", MediaItem.class)
                .getResultList();
    }

    public List<MediaItem> findAllWithNameList(List<String> nameList){
        return em.createQuery("select mi from MediaItem mi" +
                " where mi.name in :nameList", MediaItem.class)
                .setParameter("nameList", nameList)
                .getResultList();
    }
}
