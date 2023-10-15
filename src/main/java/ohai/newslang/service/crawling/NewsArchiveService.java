package ohai.newslang.service.crawling;

import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsArchiveService {
    Long save(NewsArchive newsArchive);
    void saveAll(List<NewsArchive> newsArchiveList);
    boolean isExistUrl(String url);
    List<String> isAlreadyExistUrl(List<String> urlList);
    NewsArchive findByUrl(String url);
    Page<NewsArchive> findAll(int page, int limit);
    Page<NewsArchive> getNewsArchiveList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList, int page, int limit);
}
