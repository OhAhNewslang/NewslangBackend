package ohai.newslang.domain.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto {
    private RequestResult result;

    @Builder
    public ResultDto(String resultCode, String resultMessage) {
        this.result = RequestResult.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }
}
