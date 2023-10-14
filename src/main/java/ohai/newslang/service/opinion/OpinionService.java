package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionPagingResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;

public interface OpinionService {
    RequestResult resistOpinion(OpinionCreateRequestDto opinionCreateRequestDto);
    OpinionPagingResponseDto opinionListByLikeCountOrderForDetailNews(
            String newsUrl,
            int pageNumber,
            int pageSize);

    OpinionPagingResponseDto opinionListByRecentOrderForDetailNews(
            String newsUrl,
            int pageNumber,
            int pageSize);

    OpinionPagingResponseDto opinionListByLikeCountOrderForMember(int pageNumber,
                                                 int pageSize);

    OpinionPagingResponseDto opinionListByRecentOrderForMember(int pageNumber,
                                                 int pageSize);
    ModifyOpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto);
    RequestResult deleteOpinion(Long opinionId);
}
