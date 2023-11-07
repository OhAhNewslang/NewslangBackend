package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.vo.MemberOpinionStatus;

import java.util.List;
import java.util.Optional;

@Getter
public class MemberOpinionStatusDto {
    private List<MemberOpinionStatus> memberOpinionStatusList;
    private RequestResult result;

    @Builder
    public MemberOpinionStatusDto(List<MemberOpinionStatus> memberOpinionStatusList, RequestResult result) {
        this.memberOpinionStatusList = memberOpinionStatusList;
        this.result = result;
    }
}
