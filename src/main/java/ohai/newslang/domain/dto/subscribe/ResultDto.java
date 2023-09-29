package ohai.newslang.domain.dto.subscribe;


import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.dto.RequestResult;

@Getter
public class ResultDto {
    private RequestResult result;

    @Builder
    public ResultDto(boolean isSuccess, String failCode) {
        this.result = new RequestResult(isSuccess, failCode);
    }
}
