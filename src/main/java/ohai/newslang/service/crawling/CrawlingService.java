package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.reference.Media;

import java.util.List;

public interface CrawlingService {
    List<ThumbnailNews> crawlingThumbnailNews(String url);
    List<Media> crawlingMedia(String url);

}