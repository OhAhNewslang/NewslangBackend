package ohai.newslang.domain.dto.request;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResultDto {
    private RequestResult result;

    @Builder
    public ResultDto(boolean isSuccess, String failCode) {
        this.result = new RequestResult(isSuccess, failCode);
    }
}
