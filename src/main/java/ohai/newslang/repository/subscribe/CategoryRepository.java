package ohai.newslang.repository.subscribe;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribeReference.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
//Cartegory
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category){
        em.persist(category);
    }

    public Category findOne(Long id){
        return em.find(Category.class, id);
    }

    public List<Category> findAll(){
        return em.createQuery("select c from Category c", Category.class)
                .getResultList();
    }

    public List<Category> findAllWithNameList(List<Category> nameList){
        return em.createQuery("select c from Category c" +
                        " where c.name in :nameList", Category.class)
                .setParameter("nameList", nameList)
                .getResultList();
    }
}