package ohai.newslang.service.crawling;

import ohai.newslang.domain.dto.ThumbnailNews;

import java.util.List;

public interface CrawlingNewsService {
    List<ThumbnailNews> getNewsList(String url);
}
