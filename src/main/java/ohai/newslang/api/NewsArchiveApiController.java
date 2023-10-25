package ohai.newslang.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.news.ResponseThumbnailNewsDto;
import ohai.newslang.domain.dto.news.ResultDetailNewsDto;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeNewsDto;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.service.news.NewsArchiveService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsArchiveApiController {

    private final NewsArchiveService newsArchiveService;

    @GetMapping("/guest/live")
    public ResponseThumbnailNewsDto getLiveNews(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit) {
        return newsArchiveService.findAllLiveNews(page,limit);
    }

    @GetMapping("/subscribe")
    public ResponseThumbnailNewsDto getSubscribeNews(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit) {
        return newsArchiveService.findAllSubscribeNews(page,limit);
    }

    @GetMapping("/detail")
    public ResultDetailNewsDto getDetailNews(
        @RequestParam("newsUrl") String newsUrl) {
        return newsArchiveService.findByUrl(newsUrl);
    }
}