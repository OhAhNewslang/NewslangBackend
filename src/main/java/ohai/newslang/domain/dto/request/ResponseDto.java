package ohai.newslang.domain.dto.request;


import lombok.*;

@Data
@NoArgsConstructor
public class ResponseDto {
    private RequestResult result;
    private String resultMessage;

    @Builder
    public ResponseDto(RequestResult result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }
}
