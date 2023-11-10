package ohai.newslang.domain.dto.gpt;

import lombok.Data;

@Data
public class GptApiKeyDto {
    private String apiKey;
    private String translationKey;
}
