package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.news.*;
import ohai.newslang.domain.dto.page.PageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeNewsDto;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.service.crawling.NewsArchiveService;
import ohai.newslang.service.memeber.MemberService;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NewsArchiveApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final MemberService memberService;
    private final NewsArchiveService newsArchiveService;

    @GetMapping("/api/news/live")
    public ResultThumbnailNewsDto getLiveNews(@RequestParam("page") int page, @RequestParam("limit") int limit){
        // to do list
        // 예외 처리 필요
        Page<NewsArchive> newsArchivePage = newsArchiveService.findAll(page, limit);
        List<ThumbnailNewsDto> thumbnailNewsDtoList = getThumbnailNewsDtoList(newsArchivePage);
        // 정렬 진행
        return ResultThumbnailNewsDto.builder()
                .thumbnailNewsList(thumbnailNewsDtoList)
                .pageSource(PageSourceDto.builder().page(newsArchivePage.getPageable().getPageNumber()).limit(newsArchivePage.getPageable().getPageSize()).totalPage(newsArchivePage.getTotalPages()).build())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build())
                .build();
    }

    @GetMapping("/api/news/subscribe")
    public ResultThumbnailNewsDto getSubscribeNews(@RequestBody @Valid RequestSubscribeNewsDto request) {
        Long memberId = memberService.getMemberId(request.getLoginId());
        List<String> mediaNameList = this.memberSubscribeItemService.getSubscribeMediaNameList(memberId);
        List<String> categoryNameList = this.memberSubscribeItemService.getCategoryNameList(memberId);
        List<String> keywordNameList = this.memberSubscribeItemService.getKeywordNameList(memberId);
        Page<NewsArchive> newsArchivePage = newsArchiveService.getNewsArchiveList(mediaNameList, categoryNameList, keywordNameList, request.getPageSource().getPage(), request.getPageSource().getLimit());
        List<ThumbnailNewsDto> collect = getThumbnailNewsDtoList(newsArchivePage);

        return ResultThumbnailNewsDto.builder()
                .thumbnailNewsList(collect)
                .pageSource(PageSourceDto.builder().page(newsArchivePage.getPageable().getPageNumber()).limit(newsArchivePage.getPageable().getPageSize()).totalPage(newsArchivePage.getTotalPages()).build())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build())
                .build();
    }

    @GetMapping("/api/news/detail")
    public ResultDetailNewsDto getDetailNews(@RequestParam("url") String url){
        NewsArchive newsArchive = newsArchiveService.findByUrl(url);
        if (newsArchive == null){
            return ResultDetailNewsDto.builder().result(RequestResult.builder().resultMessage("Not found url").resultCode("501").build()).build();
        }
        return ResultDetailNewsDto.builder()
                .url(newsArchive.getUrl())
                .title(newsArchive.getTitle())
                .contents(newsArchive.getContents())
                .media(newsArchive.getMediaName())
                .postDateTime(newsArchive.getPostDateTime())
                .modifyDateTime(newsArchive.getModifyDateTime())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    private static List<ThumbnailNewsDto> getThumbnailNewsDtoList(Page<NewsArchive> newsArchivePage) {
        List<ThumbnailNewsDto> thumbnailNewsDtoList = newsArchivePage.stream()
                .map(n -> {
                    return ThumbnailNewsDto.builder().url(n.getUrl()).media(n.getMediaName()).category(n.getCategory()).title(n.getTitle()).summary("").imagePath(n.getImagePath()).postDateTime(n.getPostDateTime()).build();
                }).collect(Collectors.toList());
        return thumbnailNewsDtoList;
    }
}