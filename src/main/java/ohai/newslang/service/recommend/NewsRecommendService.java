package ohai.newslang.service.recommend;

import ohai.newslang.domain.dto.recommend.newsRecommend.NewsRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.recommend.DetailNewsRecommend;

public interface NewsRecommendService {
    RequestResult updateRecommendStatus(NewsRecommendDto newsRecommendDto);
    DetailNewsRecommend createRecommendInfo(NewsRecommendDto newsRecommendDto);
}
