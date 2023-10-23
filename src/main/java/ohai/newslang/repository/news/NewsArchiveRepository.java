package ohai.newslang.repository.news;

import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NewsArchiveRepository extends JpaRepository<NewsArchive, Long> {
    @Query("select count(na.id) from NewsArchive na where na.url = :url")
    Long countByUrl(@Param("url") String url);

    @Query("select na.url from NewsArchive na where na.url in :urlList")
    List<String> alreadyExistByUrl(@Param("urlList") List<String> urlList);

    @Query("select na from NewsArchive na where na.url = :url")
    NewsArchive findByUrl(@Param("url") String url);

    NewsArchive findNewsArchiveByUrl(String url);

    Page<NewsArchive> findAll(Pageable pageable);

    @Query(value = "select * from News_Archive" +
            " where media_name in :mediaNameList" +
            " and category in :categoryList" +
            " and (contents REGEXP :keywords)",
           nativeQuery = true)
    Page<NewsArchive> findAllByFilters(@Param("mediaNameList") List<String> mediaNameList,
                                    @Param("categoryList") List<String> categoryList,
                                    @Param("keywords") String keywords,
                                    Pageable pageable);
}
