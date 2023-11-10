package ohai.newslang.service.gpt;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.gpt.ChatRequest;
import ohai.newslang.domain.dto.gpt.ChatResponse;
import ohai.newslang.domain.dto.gpt.SummarizeNewsDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.properties.GptProperties;
import ohai.newslang.repository.news.NewsArchiveRepository;
import ohai.newslang.repository.properties.GptPropertiesRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GptServiceImpl implements GptService{

    private final NewsArchiveRepository newsArchiveRepository;
    private final GptPropertiesRepository gptPropertiesRepository;

    @Override
    @Transactional
    public SummarizeNewsDto summarizeNews(String newsUrl) {

        NewsArchive newsArchive = newsArchiveRepository.findByUrl(newsUrl);
        if (newsArchive != null){
            String summary = newsArchive.getSummary();
            if (summary != null && summary.trim() != "") {
                return SummarizeNewsDto.builder()
                        .answer(summary)
                        .result(RequestResult.builder()
                                .resultCode("200")
                                .resultMessage("")
                                .build())
                        .build();
            }
        }

        try {
            List<GptProperties> gptPropertiesList = gptPropertiesRepository.findAll();
            if (gptPropertiesList.size() < 1) throw new Exception("Not found gpt properties");
            GptProperties gptProperties = gptPropertiesList.get(0);

            String questionEng = translation(gptProperties.getTranslationKey(), newsArchive.getContents());

            String prompt = "You are a professional journalist.\n" +
                    "Answers will be in Korean as a list of five items and less than 500 characters.\n\n" +
                    "--- Question ---\n" +
                    "Summarize the following context.\n\n" +
                    "--- Context ---\n" +
                    questionEng;

            // create a request
            ChatRequest chatRequest = new ChatRequest(gptProperties.getModel(), prompt, gptProperties.getTopK(), gptProperties.getTemperature());
            // create a rest template
            RestTemplate restTemplate = new RestTemplate();
            ChatResponse response = restTemplate.postForObject(gptProperties.getApiUrl(), getHttpEntity(chatRequest, gptProperties.getApiKey()), ChatResponse.class);

//        restTemplate.getInterceptors().add((request, body, execution) -> {
//            request.getHeaders().add("Authorization", "Bearer " + gptProperties.getApiKey());
//            return execution.execute(request, body);
//        });
//
//        ChatResponse response = restTemplate.postForObject(gptProperties.getApiUrl(), chatRequest, ChatResponse.class);

            if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
                throw new Exception("No response");
            }

            // return the first response
            String answer = response.getChoices().get(0).getMessage().getContent();
            if (newsArchive != null) newsArchive.updateSummary(answer);

            return SummarizeNewsDto.builder()
                    .answer(answer)
                    .result(RequestResult.builder()
                            .resultCode("200")
                            .resultMessage("")
                            .build())
                    .build();
        }
        catch (Exception ex){
            return SummarizeNewsDto.builder()
                .answer(ex.getMessage())
                .result(RequestResult.builder()
                        .resultCode("601")
                        .resultMessage(ex.getMessage())
                        .build())
                .build();
        }
    }

    private HttpEntity<ChatRequest> getHttpEntity(ChatRequest chatRequest, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);
        HttpEntity<ChatRequest> httpRequest = new HttpEntity<>(chatRequest, headers);
        return httpRequest;
    }

    private String translation(String apiKey, String sourceText){
//        System.setProperty("GOOGLE_API_KEY", apiKey);
//        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translate translate = TranslateOptions.newBuilder().setApiKey(apiKey).build().getService();
        Translation translation =
            translate.translate(
                    sourceText,
                    Translate.TranslateOption.sourceLanguage("ko"),
                    Translate.TranslateOption.targetLanguage("en"),
                    // Use "base" for standard edition, "nmt" for the premium model.
                    Translate.TranslateOption.model("base"));
        String translatedText = translation.getTranslatedText();
        return translatedText;
    }
}