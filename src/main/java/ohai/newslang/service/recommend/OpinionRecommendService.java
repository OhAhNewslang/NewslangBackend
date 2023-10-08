package ohai.newslang.service.recommend;

import ohai.newslang.domain.dto.recommend.opinionRecommend.OpinionRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.recommend.OpinionRecommend;
import ohai.newslang.domain.enumulate.RecommendStatus;

public interface OpinionRecommendService {
    RequestResult updateRecommendStatus(OpinionRecommendDto opinionRecommendDto);
    OpinionRecommend createRecommendInfo(OpinionRecommendDto opinionRecommendDto);
    Long countRecommend(OpinionRecommendDto opinionRecommendDto);


}
