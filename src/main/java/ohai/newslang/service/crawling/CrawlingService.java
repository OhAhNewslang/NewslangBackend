package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;

import java.util.List;

public interface CrawlingService {
    List<ThumbnailNews> crawling(String url);

}
