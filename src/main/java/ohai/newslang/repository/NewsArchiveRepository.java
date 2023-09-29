package ohai.newslang.repository;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NewsArchiveRepository {

    private final EntityManager em;

    public void save(NewsArchive item){
        em.persist(item);
    }

    public boolean isExistNewsUrl(String url){
        Long result = (Long)em.createQuery("select count(na.id) from NewsArchive na where na.news.url = :url")
                .setParameter("url", url)
                .getSingleResult();
        return ((result.equals(0L)) ? false : true);
    }

    public NewsArchive findOne(Long id){
        return em.find(NewsArchive.class, id);
    }

    public NewsArchive findByUrl(String url){
        return em.createQuery(
                        "select na from NewsArchive na" +
                                " where na.news.url = :url", NewsArchive.class)
                .setParameter("url", url)
                .getSingleResult();
    }

    public List<NewsArchive> findAllWithUrls(List<String> urlList){
        return em.createQuery(
                        "select na from NewsArchive na" +
                                " where na.news.url in :urlList", NewsArchive.class)
                .setParameter("urlList", urlList)
                .getResultList();
    }

    public List<NewsArchive> findAllWithNameList(List<String> mediaNameList, List<String> categoryNameList){
        return em.createQuery(
                        "select na from NewsArchive na" +
                                " where na.news.mediaName in :mediaNameList" +
                                " and na.news.categoryName in :categoryNameList", NewsArchive.class)
                .setParameter("mediaNameList", mediaNameList)
                .setParameter("categoryNameList", categoryNameList)
                .getResultList();
    }
}
