package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.request.OpinionResistRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionListResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;

public interface OpinionService {
    OpinionResponseDto resistOpinion(OpinionResistRequestDto opinionResistRequestDto);
    OpinionListResponseDto opinionListByLikeCountOrderForDetailNews(
            String newsUrl, int pageNumber, int pageSize);

    OpinionListResponseDto opinionListByRecentOrderForDetailNews(
            String newsUrl, int pageNumber, int pageSize);

    OpinionListResponseDto opinionListByLikeCountOrderForMember(
            int pageNumber, int pageSize);

    OpinionListResponseDto opinionListByRecentOrderForMember(
            int pageNumber, int pageSize);
    ModifyOpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto);
    RequestResult deleteOpinion(String opinionId);
}
