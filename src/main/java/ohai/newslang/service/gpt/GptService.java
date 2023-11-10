package ohai.newslang.service.gpt;

import ohai.newslang.domain.dto.gpt.SummarizeNewsDto;
import ohai.newslang.domain.dto.request.RequestResult;

public interface GptService {
    SummarizeNewsDto summarizeNews(String newsUrl);
}
