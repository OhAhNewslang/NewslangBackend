package ohai.newslang.service.scrap;

import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.scrap.ResultScrapNewsDto;

public interface MemberScrapNewsService {

    ResultScrapNewsDto scarpNewsList(int page, int limit);
    RequestResult addScrapNews(String newsUrl);
    void removeScrapNews(String url);
}
