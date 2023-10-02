package ohai.newslang.service.crawling;

import ohai.newslang.domain.entity.news.NewsArchive;

import java.util.List;

public interface NewsArchiveService {
    Long save(NewsArchive newsArchive);
    boolean isExistUrl(String url);
    NewsArchive findByUrl(String url);
    List<NewsArchive> getNewsArchiveList(List<String> mediaNameList, List<String> categoryNameList, List<String> keywordNameList);
}
