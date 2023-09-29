package ohai.newslang.service.crawling;

import ohai.newslang.domain.dto.ThumbnailNews;
import ohai.newslang.domain.entity.subscribeReference.Media;

import java.util.List;

public interface CrawlingService {
    List<ThumbnailNews> crawlingThumbnailNews(String url);
    List<Media> crawlingMedia(String url);

}