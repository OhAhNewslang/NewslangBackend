package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

public interface OpinionService {
    RequestResult resistOpinion(OpinionCreateRequestDto opinionCreateRequestDto);
    List<OpinionResponseDto> opinionListByDetailNews(Long detailNewsId);
    List<OpinionResponseDto> opinionListByMember();
    OpinionResponseDto modifyContent(OpinionModifyRequestDto opinionModifyRequestDto);
    RequestResult deleteOpinion(Long opinionId);
}
