package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.News;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.dto.RequestResult;
import ohai.newslang.domain.dto.ScrapNews;
import ohai.newslang.domain.entity.scrap.MemberScrapNewsArchive;
import ohai.newslang.domain.dto.scrap.RequestScrapNewsDto;
import ohai.newslang.domain.dto.scrap.ResultScrapNewsDto;
import ohai.newslang.domain.dto.subscribe.ResultDto;
import ohai.newslang.service.crawling.NewsArchiveService;
import ohai.newslang.service.scrap.MemberScrapNewsService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ScrapNewsApiController {

    private final MemberScrapNewsService memberScrapNewsService;
    private final NewsArchiveService newsArchiveService;

    @PostMapping("/api/news/scrap/{id}")
    public ResultDto scrapNews(@PathVariable("id") Long id, @RequestBody @Valid RequestScrapNewsDto request){
        return this.getScrapNewsDto(id, request, true);
    }

    @DeleteMapping("/api/news/scrap/{id}")
    public ResultDto removeScrapNews(@PathVariable("id") Long id, @RequestBody @Valid RequestScrapNewsDto request){
        return this.getScrapNewsDto(id, request, false);
    }

    @GetMapping("/api/news/scrap/{id}")
    public ResultScrapNewsDto getScrapNews(@PathVariable("id") Long id){
        return this.getScrapNewsDto(id);
    }

    private ResultDto getScrapNewsDto(Long id, RequestScrapNewsDto request, boolean isScrap){
        try {
            if (isScrap){
                NewsArchive newsArchive = newsArchiveService.findByUrl(request.getUrl());
                memberScrapNewsService.addNewsArchive(id, newsArchive);
            }else{
                memberScrapNewsService.removeNewsArchive(id, request.getUrl());
            }
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().isSuccess(false).failCode("").build();
        }
        return ResultDto.builder().isSuccess(true).failCode("").build();
    }

    private ResultScrapNewsDto getScrapNewsDto(Long id){
        List<ScrapNews> scrapNewsList = new ArrayList<>();
        try {
            List<MemberScrapNewsArchive> memberScrapNewsArchiveList = memberScrapNewsService.findNewsArchiveList(id);
            List<ScrapNews> findScrapNewsList = memberScrapNewsArchiveList.stream()
                    .map(o -> {
                        News n = o.getNewsArchive().getNews();
                        return ScrapNews
                                .builder()
                                .url(n.getUrl())
                                .mediaName(n.getMediaName())
                                .writer("")
                                .title(n.getTitle())
                                .contents(n.getContents())
                                .scrapDate(o.getScrapDate())
                                .build();
                    })
                    .collect(Collectors.toList());
            scrapNewsList.addAll(findScrapNewsList);
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultScrapNewsDto.builder().memberId(id).scrapNewsList(null).result(RequestResult.builder().isSuccess(false).failCode("").build()).build();
        }
        return ResultScrapNewsDto.builder().memberId(id).scrapNewsList(scrapNewsList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }
}