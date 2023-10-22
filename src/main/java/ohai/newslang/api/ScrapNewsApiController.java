package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.page.PageSourceDto;
import ohai.newslang.domain.dto.request.ResultDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.scrap.*;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.service.crawling.NewsArchiveService;
import ohai.newslang.service.memeber.MemberService;
import ohai.newslang.service.scrap.MemberScrapNewsService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapNewsApiController {

    private final MemberScrapNewsService memberScrapNewsService;
    private final NewsArchiveService newsArchiveService;
    private final TokenDecoder tokenDecoder;

    @PostMapping("/news")
    public ResultDto scrapNews(@RequestBody @Valid RequestScrapNewsDto request){
        Long memberId = tokenDecoder.currentUserId();
        if (memberScrapNewsService.isExistScrapNews(memberId, request.getNewsUrl())){
            return ResultDto.builder().resultMessage("Already exist scrap news").resultCode("301").build();
        }
        NewsArchive newsArchive = newsArchiveService.findByUrl(request.getNewsUrl());
        memberScrapNewsService.addScrapNews(memberId, newsArchive.getId());
        return ResultDto.builder().resultMessage("").resultCode("200").build();
    }

    @GetMapping("/news")
    public ResultScrapNewsDto getScrapNews(@RequestBody @Valid RequestScrapNewsPageDto request){
        Long memberId = tokenDecoder.currentUserId();
        Page<MemberScrapNewsArchive> memberScrapNewsArchivePageList = memberScrapNewsService.getNewsArchiveList(memberId, request.getPage(), request.getLimit());
        List<ScrapNewsDto> collect = memberScrapNewsArchivePageList.stream()
                .map(n -> {
                    NewsArchive newsArchive = n.getNewsArchive();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    return ScrapNewsDto.builder().newsUrl(newsArchive.getUrl())
                            .mediaName(newsArchive.getMediaName())
                            .category(newsArchive.getCategory())
                            .title(newsArchive.getTitle())
                            .imagePath(newsArchive.getImagePath())
                            .postDateTime(newsArchive.getPostDateTime().format(dateFormat))
                            .scrapDateTime(n.getScrapDateTime().format(dateFormat))
                            .build();
                }).collect(Collectors.toList());
        return ResultScrapNewsDto.builder().scrapNewsList(collect)
                .pageSource(PageSourceDto.builder().page(memberScrapNewsArchivePageList.getPageable().getPageNumber()).limit(memberScrapNewsArchivePageList.getPageable().getPageSize()).totalPage(memberScrapNewsArchivePageList.getTotalPages()).build())
                .result(RequestResult.builder().resultMessage("").resultCode("200").build()).build();
    }

    @DeleteMapping("/news")
    public ResultDto removeScrapNews(@RequestBody @Valid RequestRemoveScrapNewsDto request){
        Long memberId = tokenDecoder.currentUserId();
        memberScrapNewsService.removeScrapNews(memberId, request.getNewsUrl());
        return ResultDto.builder().resultMessage("").resultCode("200").build();
    }
}