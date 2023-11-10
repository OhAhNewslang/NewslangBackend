package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.gpt.GptApiKeyDto;
import ohai.newslang.domain.dto.gpt.SummarizeNewsDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.gpt.GptService;
import ohai.newslang.service.properties.PropertiesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final GptService gptService;
    private final PropertiesService propertiesService;

//    @Qualifier("openaiRestTemplate")
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${openai.model}")
//    private String model;
//
//    @Value("${openai.api.url}")
//    private String apiUrl;

    @GetMapping("/news/summarize")
    public SummarizeNewsDto summarizeNews(
            @RequestParam String newUrl) {
        return gptService.summarizeNews(newUrl);
    }

    @PostMapping("/admin/gpt/k")
    public RequestResult setApiKey(@RequestBody GptApiKeyDto request){
        return propertiesService.setGptProperties(request.getApiKey(), request.getTranslationKey());
    }
}
