package ohai.newslang.service.crawling;

import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.subscribe.item.MediaItem;

import java.util.List;

public interface CrawlingService {
    List<ThumbnailNews> crawlingThumbnailNews(String url);
    List<MediaItem> crawlingMedia(String url);

}
