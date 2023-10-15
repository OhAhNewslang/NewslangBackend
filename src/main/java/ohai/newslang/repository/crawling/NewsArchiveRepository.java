package ohai.newslang.repository.crawling;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import java.util.List;

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

    @Query("select count(na.id) from NewsArchive na where na.url = :url")
    Long countByUrl(@Param("url") String url);

    @Query("select na.url from NewsArchive na where na.url in :urlList")
    List<String> alreadyExistByUrl(@Param("urlList") List<String> urlList);

    @Query("select na from NewsArchive na where na.url = :url")
    NewsArchive findByUrl(@Param("url") String url);

    Page<NewsArchive> findAll(Pageable pageable);

    @Query(value = "select * from News_Archive" +
            " where media_name in :mediaNameList" +
            " and category in :categoryList" +
            " and (contents REGEXP :keywords)",
           nativeQuery = true)
    Page<NewsArchive> findByFilters(@Param("mediaNameList") List<String> mediaNameList,
                                    @Param("categoryList") List<String> categoryList,
                                    @Param("keywords") String keywords,
                                    Pageable pageable);

//    @Query("select na from NewsArchive na where na.mediaName in :mediaNameList and na.category in :categoryList and na.contents in :keywordList")
//    Page<NewsArchive> findByFilters(@Param("mediaNameList") List<String> mediaNameList,
//                                    @Param("categoryList") List<String> categoryList,
//                                    @Param("keywordList") List<String> keywordList,
//                                    Pageable pageable);


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
