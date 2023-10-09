package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.DetailNews;
import ohai.newslang.domain.ThumbnailNews;
import ohai.newslang.domain.dto.news.*;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeNewsDto;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.crawling.CrawlingNewsService;
import ohai.newslang.service.memeber.MemberService;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NewsArchiveApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final MediaService mediaService;
    private final CrawlingNewsService crawlingNewsService;
    private final MemberService memberService;

    @GetMapping("/api/news/live")
    public ResultSubscribeNewsDto getLiveNews(@PathVariable("page") int limitPage){
        // to do list
        // 예외 처리 필요
        List<ThumbnailNews> collect = this.getTodayNews(limitPage);
        List<ThumbnailNewsDto> thumbnailNewsDtoList = collect.stream()
                .map(n -> {
                    return ThumbnailNewsDto.builder().url(n.getLink()).media(n.getMedia()).category(n.getCategory()).title(n.getTitle()).summary(n.getSummary()).imagePath(n.getImagePath()).postDateTime(n.getPostDateTime()).isSubscribeNews(false).build();
                }).collect(Collectors.toList());
        // 정렬 진행
        return ResultSubscribeNewsDto.builder().thumbnailNewsList(thumbnailNewsDtoList).result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    @GetMapping("/api/news/subscribe")
    public ResultSubscribeNewsDto getSubscribeNews(@RequestParam @Valid RequestSubscribeNewsDto request) {
        Long memberId = memberService.getMemberId(request.getLoginId());
        List<String> mediaNameList = this.memberSubscribeItemService.getSubscribeMediaNameList(memberId);
        List<String> categoryNameList = this.memberSubscribeItemService.getCategoryNameList(memberId);
        List<String> keywordNameList = this.memberSubscribeItemService.getKeywordNameList(memberId);
        List<Media> mediaList = mediaService.findAll();
        List<ThumbnailNews> thumbnailNewsList = crawlingNewsService.getThumbnailNewsList(mediaList, request.getPostDate(), request.getLimitPage());
        List<ThumbnailNewsDto> collect = thumbnailNewsList.stream()
                .map(n -> {
                    if ((mediaNameList.size() > 0 && mediaNameList.contains(n.getMedia())) ||
                        (categoryNameList.size() > 0 && categoryNameList.contains(n.getCategory())) ||
                        (keywordNameList.size() > 0 && keywordNameList.contains(n.getContents()))
                    ) {
                        return ThumbnailNewsDto.builder().url(n.getLink()).media(n.getMedia()).category(n.getCategory()).title(n.getTitle()).summary(n.getSummary()).imagePath(n.getImagePath()).postDateTime(n.getPostDateTime()).isSubscribeNews(true).build();
                    }
                    return null;
                }).collect(Collectors.toList());

        // 정렬 진행

        return ResultSubscribeNewsDto.builder().thumbnailNewsList(collect).result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    @GetMapping("/api/news/main")
    public ResultSubscribeNewsDto getMainNews(@RequestParam @Valid RequestSubscribeNewsDto request) {
        Long memberId = memberService.getMemberId(request.getLoginId());
        List<String> mediaNameList = this.memberSubscribeItemService.getSubscribeMediaNameList(memberId);
        List<String> categoryNameList = this.memberSubscribeItemService.getCategoryNameList(memberId);
        List<String> keywordNameList = this.memberSubscribeItemService.getKeywordNameList(memberId);
        List<ThumbnailNews> thumbnailNewsList = this.getTodayNews(request.getLimitPage());
        List<ThumbnailNewsDto> thumbnailNewsDtoList = thumbnailNewsList.stream()
                .map(n -> {
                    boolean isSubscribeNews = false;
                    if ((mediaNameList.size() > 0 && mediaNameList.contains(n.getMedia())) ||
                            (categoryNameList.size() > 0 && categoryNameList.contains(n.getCategory())) ||
                            (keywordNameList.size() > 0 && keywordNameList.contains(n.getContents()))
                    ) {
                        isSubscribeNews = true;
                    }
                    return ThumbnailNewsDto.builder().url(n.getLink()).media(n.getMedia()).category(n.getCategory()).title(n.getTitle()).summary(n.getSummary()).imagePath(n.getImagePath()).postDateTime(n.getPostDateTime()).isSubscribeNews(isSubscribeNews).build();
                }).collect(Collectors.toList());

        return ResultSubscribeNewsDto.builder().thumbnailNewsList(thumbnailNewsDtoList).result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    @GetMapping("/api/news/detail")
    public ResultDetailNewsDto getDetailNews(@RequestParam @Valid RequestDetailNewsDto request){
        DetailNews detailNews = crawlingNewsService.getDetailNewsList(request.getUrl(), request.getMedia());
        return ResultDetailNewsDto.builder().url(detailNews.getLink()).title(detailNews.getTitle()).contents(detailNews.getContents()).media(detailNews.getMedia()).postDateTime(detailNews.getPostDateTime()).modifyDateTime(detailNews.getModifyDateTime())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    private List<ThumbnailNews> getTodayNews(int limitPage){
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
        String strDate = LocalDate.now().format(dateFormat);
        List<Media> mediaList = mediaService.findAll();
        return crawlingNewsService.getThumbnailNewsList(mediaList, strDate, limitPage);
    }
}