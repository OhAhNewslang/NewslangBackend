package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.request.OpinionResistRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.*;
import ohai.newslang.domain.dto.request.RequestResult;

public interface OpinionService {
    ResistOpinionResponseDto resistOpinion(OpinionResistRequestDto opinionResistRequestDto);

    MemberOpinionStatusDto opinionStatusForNews(String newsUrl);

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
