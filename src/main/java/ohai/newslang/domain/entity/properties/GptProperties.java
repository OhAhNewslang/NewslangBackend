package ohai.newslang.domain.entity.properties;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class GptProperties {
    @Id
    @GeneratedValue
    @Column(name = "gpt_properties_id")
    private Long id;

    @Column
    private String model;

    @Column
    private String apiUrl;

    @Column
    private String apiKey;

    @Column
    private String translationKey;

    @Column
    private int topK;

    @Column
    private double temperature;

    public void setModel(String model) {
        this.model = model;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public void setTopK(int topK) {
        this.topK = topK;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public static GptProperties createGptProperties(String apiKey, String translationKey){
        GptProperties gptProperties = new GptProperties();
        gptProperties.model = "gpt-3.5-turbo";
        gptProperties.apiUrl = "https://api.openai.com/v1/chat/completions";
        gptProperties.topK = 1;
        gptProperties.temperature = 0.2;
        gptProperties.apiKey = apiKey;
        gptProperties.translationKey = translationKey;
        return gptProperties;
    }
}
