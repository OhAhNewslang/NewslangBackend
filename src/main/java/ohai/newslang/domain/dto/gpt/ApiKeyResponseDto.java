package ohai.newslang.domain.dto.gpt;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
public class ApiKeyResponseDto {
    private GptApiKeyDto apiKeys;
    private RequestResult result;

    @Builder
    public ApiKeyResponseDto(GptApiKeyDto apiKeys, RequestResult result) {
        this.apiKeys = apiKeys;
        this.result = result;
    }
}
