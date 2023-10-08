package ohai.newslang.service.recommend;

import ohai.newslang.domain.dto.recommend.newsRecommend.NewsRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.recommend.NewsRecommend;

public interface NewsRecommendService {
    RequestResult updateRecommendStatus(NewsRecommendDto newsRecommendDto);
    NewsRecommend createRecommendInfo(NewsRecommendDto newsRecommendDto);
    Long countRecommend(NewsRecommendDto newsRecommendDto);

}
