package ohai.newslang.service.crawling;

import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;

import java.util.List;

public interface CrawlingMediaService {
    List<Media> getMediaList(String url);
}
