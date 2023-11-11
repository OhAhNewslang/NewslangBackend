package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.gpt.ApiKeyResponseDto;
import ohai.newslang.domain.dto.gpt.GptApiKeyDto;
import ohai.newslang.domain.dto.gpt.SummarizeNewsDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.gpt.GptService;
import ohai.newslang.service.properties.PropertiesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class LLMController {

    private final GptService gptService;
    private final PropertiesService propertiesService;

    @GetMapping("/news/summarize")
    public SummarizeNewsDto summarizeNews(
            @RequestParam String newUrl) {
        return gptService.summarizeNews(newUrl);
    }

    @GetMapping("/admin/gpt/k")
    public ApiKeyResponseDto getApiKey(){
        return propertiesService.getApiKeys();
    }

    @PostMapping("/admin/gpt/k")
    public RequestResult setApiKey(@RequestBody GptApiKeyDto request){
        return propertiesService.setGptProperties(request.getApiKey(), request.getTranslationKey());
    }
}
