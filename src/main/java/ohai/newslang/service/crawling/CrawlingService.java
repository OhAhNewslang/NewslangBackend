package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.item.Media;

import java.util.List;

public interface CrawlingService {
    List<ThumbnailNews> crawlingThumbnailNews(String url);
    List<Media> crawlingMedia(String url);

}
