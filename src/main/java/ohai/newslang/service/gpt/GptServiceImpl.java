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
            String answer = getAnswer(gptProperties,
                    "",
                    "You are a professional journalist.\n" +
                                "Extract keywords by referring to the provided context, summarize the brief in 50 characters or less, and summarize the entire document in 300 characters or less.\n" +
                                "Please refer to the answer format below.\n" +
                                "Answers will be in Korean.\n" +
                                "1. 키워드\n" +
                                "   - 마이크로소프트\n" +
                                "   - Bing\n" +
                                "   - 대기 목록\n" +
                                "   - 기술\n" +
                                "\n" +
                                "2. 브리프\n" +
                                "   - 마이크로소프트\n" +
                                "   - Bing 대기 목록 없애고 GPT4 무료 접근 제공\n" +
                                "\n" +
                                "3. 본문 요약\n" +
                                "   - 마이크로소프트는 Bing 대기 목록을 제거하여 사용자들이 GPT-4에 무료로 접금할 수 있도록 결정했습니다.\n" +
                                "   - 사용자 경험 개선과 콘텐츠 생성의 효율성 향상을 목표로 합니다.\n" +
                                "   - GPT4는 인공 지능 기반의 텍스트 생성 엔진입니다.\n" +
                                "   - 마이크로소프트는 인공 지능 기반 기술 개발을 고도화합니다.\n" +
                                "Answers will be in Korean.\n\n" +
                                "--- Context ---\n" + questionEng);
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

    private String getAnswer(GptProperties gptProperties, String systemPrompt, String userPrompt){
        // create a request
        ChatRequest chatRequest = new ChatRequest(gptProperties.getModel(), systemPrompt, userPrompt, gptProperties.getTopK(), gptProperties.getTemperature());
        // create a rest template
        RestTemplate restTemplate = new RestTemplate();
        ChatResponse response = restTemplate.postForObject(gptProperties.getApiUrl(), getHttpEntity(chatRequest, gptProperties.getApiKey()), ChatResponse.class);
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) return "No response";
        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
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