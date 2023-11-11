package ohai.newslang.service.properties;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.gpt.ApiKeyResponseDto;
import ohai.newslang.domain.dto.gpt.GptApiKeyDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.properties.GptProperties;
import ohai.newslang.repository.properties.GptPropertiesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertiesServiceImpl implements PropertiesService {
    private final GptPropertiesRepository gptPropertiesRepository;
    private final TokenDecoder td;

    @Override
    @Transactional
    public RequestResult setGptProperties(String apiKey, int topK, double temperature) {
        List<GptProperties> gptPropertiesList = gptPropertiesRepository.findAll();
        boolean isAlreadyProperties = gptPropertiesList.size() > 0;
        if (isAlreadyProperties){
            GptProperties gptProperties = gptPropertiesList.get(0);
            gptProperties.setApiKey(apiKey);
            gptProperties.setTopK(topK);
            gptProperties.setTemperature(temperature);
        }
        return getResult(isAlreadyProperties);
    }

    @Override
    @Transactional
    public RequestResult setGptProperties(int topK, double temperature) {
        List<GptProperties> gptPropertiesList = gptPropertiesRepository.findAll();
        boolean isAlreadyProperties = gptPropertiesList.size() > 0;
        if (isAlreadyProperties){
            GptProperties gptProperties = gptPropertiesList.get(0);
            gptProperties.setTopK(topK);
            gptProperties.setTemperature(temperature);
        }
        return getResult(isAlreadyProperties);
    }

    @Override
    @Transactional
    public RequestResult setGptProperties(String apiKey, String translationKey) {
        List<GptProperties> gptPropertiesList = gptPropertiesRepository.findAll();
        boolean isAlreadyProperties = gptPropertiesList.size() > 0;
        if (isAlreadyProperties){
            GptProperties gptProperties = gptPropertiesList.get(0);
            gptProperties.setApiKey(apiKey);
            gptProperties.setTranslationKey(translationKey);
        }
        return getResult(isAlreadyProperties);
    }

    @Override
    public ApiKeyResponseDto getApiKeys() {
        List<GptProperties> gptPropertiesList = gptPropertiesRepository.findAll();
        GptApiKeyDto gptApiKeyDto = GptApiKeyDto.builder()
                .apiKey("")
                .translationKey("")
                .build();
        boolean isAlreadyProperties = gptPropertiesList.size() > 0;
        if (isAlreadyProperties){
            GptProperties gptProperties = gptPropertiesList.get(0);
            gptApiKeyDto.setApiKey(gptProperties.getApiKey());
            gptApiKeyDto.setTranslationKey(gptProperties.getTranslationKey());
        }
        RequestResult result = getResult(isAlreadyProperties);
        return ApiKeyResponseDto.builder()
                .apiKeys(gptApiKeyDto)
                .result(result)
                .build();
    }

    private RequestResult getResult(boolean alreadyData){
        String resultCode = "200";
        String resultMessage = "";
        if (!alreadyData){
            resultCode = "600";
            resultMessage = "Not found admin properties";
        }
        return RequestResult.builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }
}
