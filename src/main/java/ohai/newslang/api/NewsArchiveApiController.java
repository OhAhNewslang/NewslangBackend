package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.news.*;
import ohai.newslang.domain.dto.page.PageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeNewsDto;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.service.crawling.NewsArchiveService;
import ohai.newslang.service.memeber.MemberService;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class NewsArchiveApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final NewsArchiveService newsArchiveService;
    private final TokenDecoder tokenDecoder;

    @GetMapping("/guest/live")
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

    @GetMapping("/subscribe")
    public ResultThumbnailNewsDto getSubscribeNews(@RequestBody @Valid RequestSubscribeNewsDto request) {
        Long memberId = tokenDecoder.currentUserId();
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemService.getMemberSubscribeItem(memberId);
        List<String> mediaNameList = memberSubscribeItem.getMemberSubscribeMediaItemList().stream().map(s -> s.getMedia().getName()).collect(Collectors.toList());
        List<String> categoryNameList = memberSubscribeItem.getSubscribeCategoryList().stream().map(s -> s.getName()).collect(Collectors.toList());
        List<String> keywordNameList = memberSubscribeItem.getSubscribeKeywordList().stream().map(s -> s.getName()).collect(Collectors.toList());
        Page<NewsArchive> newsArchivePage = newsArchiveService.getNewsArchiveList(mediaNameList, categoryNameList, keywordNameList, request.getPageSource().getPage(), request.getPageSource().getLimit());
        List<ThumbnailNewsDto> collect = getThumbnailNewsDtoList(newsArchivePage);
        return ResultThumbnailNewsDto.builder()
                .thumbnailNewsList(collect)
                .pageSource(PageSourceDto.builder().page(newsArchivePage.getPageable().getPageNumber()).limit(newsArchivePage.getPageable().getPageSize()).totalPage(newsArchivePage.getTotalPages()).build())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build())
                .build();
    }

    @GetMapping("/detail")
    public ResultDetailNewsDto getDetailNews(@RequestParam("newsUrl") String newsUrl){
        NewsArchive newsArchive = newsArchiveService.findByUrl(newsUrl);
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
                .reporter(newsArchive.getReporter())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    private static List<ThumbnailNewsDto> getThumbnailNewsDtoList(Page<NewsArchive> newsArchivePage) {
        List<ThumbnailNewsDto> thumbnailNewsDtoList = newsArchivePage.stream()
                .map(n -> ThumbnailNewsDto.builder().url(n.getUrl()).media(n.getMediaName()).category(n.getCategory()).title(n.getTitle()).summary("").imagePath(n.getImagePath()).postDateTime(n.getPostDateTime()).build()).collect(Collectors.toList());
        return thumbnailNewsDtoList;
    }
}