package ohai.newslang.service.opinion;

import ohai.newslang.domain.dto.opinion.response.OpinionDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.opinion.Opinion;

import java.util.List;

public class OpinionServiceImpl implements OpinionService{
    @Override
    public RequestResult resistOpinion(String content) {
//        Opinion.createOpinion();
        return null;
    }

    @Override
    public List<OpinionDto> opinionList(Long detailNewsId) {
        return null;
    }

    @Override
    public OpinionDto modifyContent() {
        return null;
    }

    @Override
    public RequestResult deleteOpinion() {
        return null;
    }
}
