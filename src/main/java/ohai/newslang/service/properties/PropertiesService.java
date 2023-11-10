package ohai.newslang.service.properties;

import ohai.newslang.domain.dto.request.RequestResult;

public interface PropertiesService {
    RequestResult setGptProperties(String apiKey, int topK, double temperature);
    RequestResult setGptProperties(int topK, double temperature);
    RequestResult setGptProperties(String apiKey, String translationKey);
}
