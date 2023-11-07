package ohai.newslang.service.crawling;

import ohai.newslang.domain.vo.News;

import java.util.List;

public interface CrawlingNewsService {
    List<News> getNewsList(String oId, String date, int page) throws InterruptedException;
//    DetailNews getDetailNewsList(String url, String media);
}
