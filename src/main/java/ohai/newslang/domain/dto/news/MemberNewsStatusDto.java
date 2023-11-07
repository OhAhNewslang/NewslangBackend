package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.enumulate.RecommendStatus;
import ohai.newslang.domain.vo.MemberNewsStatus;

@Getter
public class MemberNewsStatusDto {
    private MemberNewsStatus memberNewsStatus;
    private RequestResult result;

    @Builder
    public MemberNewsStatusDto(MemberNewsStatus memberNewsStatus, RequestResult result) {
        this.memberNewsStatus = memberNewsStatus;
        this.result = result;
    }
}
