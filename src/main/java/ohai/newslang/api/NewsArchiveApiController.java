package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.news.ResponseThumbnailNewsDto;
import ohai.newslang.domain.dto.news.ResultDetailNewsDto;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeNewsDto;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.service.news.NewsArchiveService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsArchiveApiController {

    private final NewsArchiveService newsArchiveService;

    @GetMapping("/guest/live/{page}/{limit}")
    public ResponseThumbnailNewsDto getLiveNews(@PathVariable("page")int page, @PathVariable("limit")int limit/*@RequestBody @Valid RequestPageSourceDto pageSourceDto*/){
        return newsArchiveService.findAllLiveNews(page,limit);
    }

    @GetMapping("/subscribe/{page}/{limit}")
    public ResponseThumbnailNewsDto getSubscribeNews(@PathVariable("page")int page, @PathVariable("limit")int limit/*@RequestBody @Valid RequestPageSourceDto pageSourceDto*/) {
        return newsArchiveService
        .findAllSubscribeNews(page,limit);
    }

    @GetMapping("/detail/{newsUrl}")
    public ResultDetailNewsDto getDetailNews(@PathVariable("newsUrl") String newsUrl){
        if (newsUrl.isBlank()) {
            return ResultDetailNewsDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage("잘못된 요청 입니다.").build()).build();
        }
        return newsArchiveService.findByUrl(newsUrl);
    }
}