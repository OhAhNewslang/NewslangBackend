package ohai.newslang.service.news;

import ohai.newslang.domain.dto.news.*;
import ohai.newslang.domain.entity.news.NewsArchive;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsArchiveService {
    void saveAll(List<NewsArchive> newsArchiveList);
    List<String> isAlreadyExistUrl(List<String> urlList);
    ResultDetailNewsDto findByUrl(String url);
    MemberNewsStatusDto findNewsStatusByUrl(String url);
    ResponseThumbnailNewsDto findAllLiveNews(int page, int limit);
    ResponseThumbnailNewsDto findAllSubscribeNews(int page, int limit);
    KeywordNewsDto findAllKeywordNews(String keyword);
}
