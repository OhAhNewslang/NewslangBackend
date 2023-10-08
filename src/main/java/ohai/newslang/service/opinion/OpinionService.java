package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.response.OpinionDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

public interface OpinionService {
    RequestResult resistOpinion(String content);
    List<OpinionDto> opinionList(Long detailNewsId);
    OpinionDto modifyContent();
    RequestResult deleteOpinion();
}
