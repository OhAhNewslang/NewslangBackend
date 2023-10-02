package ohai.newslang.domain.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto {
    private RequestResult result;
    private String resultMessage;

    @Builder
    public ResultDto(boolean isSuccess, String failCode, String resultMessage) {
        this.result = RequestResult.builder()
                .isSuccess(isSuccess)
                .failCode(failCode)
                .build();
        this.resultMessage = resultMessage;
    }
}
