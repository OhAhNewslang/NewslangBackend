package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.MediaItem;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryItemRepository {

    private final EntityManager em;

    public void save(Category category){
        em.persist(category);
    }

    public List<Category> findAll(){
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }
}
