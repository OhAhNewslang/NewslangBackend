package ohai.newslang.domain.dto.gpt;

import lombok.Builder;
import lombok.Data;

@Data
public class GptApiKeyDto {
    private String apiKey;
    private String translationKey;

    @Builder
    public GptApiKeyDto(String apiKey, String translationKey) {
        this.apiKey = apiKey;
        this.translationKey = translationKey;
    }
}
