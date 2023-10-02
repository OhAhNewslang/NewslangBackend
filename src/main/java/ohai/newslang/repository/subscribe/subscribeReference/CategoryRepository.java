package ohai.newslang.repository.subscribe.subscribeReference;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//@Repository
//@RequiredArgsConstructor
//Cartegory
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    private final EntityManager em;
//
//    public void save(Category category){
//        em.persist(category);
//    }
//
//    public Category findOne(Long id){
//        return em.find(Category.class, id);
//    }
//
//    public List<Category> findAll(){
//        return em.createQuery("select c from Category c", Category.class)
//                .getResultList();
//    }

//    public List<Category> findAllWithNameList(List<Category> nameList){
//        return em.createQuery("select c from Category c" +
//                        " where c.name in :nameList", Category.class)
//                .setParameter("nameList", nameList)
//                .getResultList();
//    }

//    Optional<Category> findByName(String... names);

    @Query("select count(c.id) from Category c where c.name = :name")
    Long countByName(@Param("name") String name);
}