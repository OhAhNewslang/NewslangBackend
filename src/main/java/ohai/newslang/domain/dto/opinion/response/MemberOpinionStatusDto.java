package ohai.newslang.domain.dto.opinion.response;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.vo.MemberOpinionStatus;

@Getter
public class MemberOpinionStatusDto {
    private MemberOpinionStatus memberOpinionStatus;
    private RequestResult result;

    @Builder
    public MemberOpinionStatusDto(MemberOpinionStatus memberOpinionStatus, RequestResult result) {
        this.memberOpinionStatus = memberOpinionStatus;
        this.result = result;
    }
}
