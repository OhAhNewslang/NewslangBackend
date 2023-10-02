package ohai.newslang.repository.crawling;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import java.util.List;

//@Repository
//@RequiredArgsConstructor
public interface NewsArchiveRepository extends JpaRepository<NewsArchive, Long> {

//    private final EntityManager em;
//
//    public void save(NewsArchive item){
//        em.persist(item);
//    }

//    public boolean isExistNewsUrl(String url){
//        Long result = (Long)em.createQuery("select count(na.id) from NewsArchive na where na.news.url = :url")
//                .setParameter("url", url)
//                .getSingleResult();
//        return ((result.equals(0L)) ? false : true);
//    }

    @Query("select count(na.id) from NewsArchive na where na.news.url = :url")
    Long countByUrl(@Param("url") String url);

    @Query("select na.news.url from NewsArchive na where na.news.url in :urlList")
    List<String> alreadyExistByUrl(@Param("urlList") List<String> urlList);

    @Query("select na from NewsArchive na where na.news.url = :url")
    NewsArchive findByUrl(@Param("url") String url);

    @Query("select na from NewsArchive na where na.news.mediaName in :mediaNameList and na.news.categoryName in :categoryNameList")
    List<NewsArchive> findByMediaNamesAndCategoryNames(@Param("mediaNameList") List<String> mediaNameList,
                                                       @Param("categoryNameList") List<String> categoryNameList);

//    public NewsArchive findOne(Long id){
//        return em.find(NewsArchive.class, id);
//    }

//    public NewsArchive findByUrl(String url){
//        return em.createQuery(
//                        "select na from NewsArchive na" +
//                                " where na.news.url = :url", NewsArchive.class)
//                .setParameter("url", url)
//                .getSingleResult();
//    }

//    public List<NewsArchive> findAllWithNameList(List<String> mediaNameList, List<String> categoryNameList){
//        return em.createQuery(
//                        "select na from NewsArchive na" +
//                                " where na.news.mediaName in :mediaNameList" +
//                                " and na.news.categoryName in :categoryNameList", NewsArchive.class)
//                .setParameter("mediaNameList", mediaNameList)
//                .setParameter("categoryNameList", categoryNameList)
//                .getResultList();
//    }
}
