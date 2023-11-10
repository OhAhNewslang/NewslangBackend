package ohai.newslang.domain.dto.gpt;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
public class SummarizeNewsDto {
    private String answer;
    private RequestResult result;

    @Builder
    public SummarizeNewsDto(String answer, RequestResult result) {
        this.answer = answer;
        this.result = result;
    }
}
