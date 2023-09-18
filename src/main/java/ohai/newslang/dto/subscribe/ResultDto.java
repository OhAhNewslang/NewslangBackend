package ohai.newslang.dto.subscribe;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.RequestResult;

@Getter
public class ResultDto {
    private RequestResult result;

    @Builder
    public ResultDto(boolean isSuccess, String failCode) {
        this.result = new RequestResult(isSuccess, failCode);
    }
}
