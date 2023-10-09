package ohai.newslang.service.crawling;

import ohai.newslang.domain.DetailNews;
import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;

import java.util.List;

public interface CrawlingNewsService {
    List<ThumbnailNews> getThumbnailNewsList(List<Media> mediaList, String date, int lastPageNo);

    DetailNews getDetailNewsList(String url, String media);
}
