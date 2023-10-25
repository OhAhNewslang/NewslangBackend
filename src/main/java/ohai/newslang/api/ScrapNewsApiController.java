package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.request.ResultDto;
import ohai.newslang.domain.dto.scrap.RequestScrapNewsDto;
import ohai.newslang.domain.dto.scrap.ResultScrapNewsDto;
import ohai.newslang.service.scrap.MemberScrapNewsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapNewsApiController {

    private final MemberScrapNewsService memberScrapNewsService;

    @PostMapping("/news")
    public RequestResult scrapNews(
        @RequestBody @Valid RequestScrapNewsDto request,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build();
        }
        return memberScrapNewsService.addScrapNews(request.getNewsUrl());
    }

    @GetMapping("/news")
    public ResultScrapNewsDto getScrapNews(
        @RequestParam("page") int page,
        @RequestParam("limit")int limit) {

        return memberScrapNewsService.scarpNewsList(page, limit);
    }

    // + a 여러개 선택해서 배열로 받아서 스크랩 취소하기

    @DeleteMapping("/news")
    public ResultDto removeScrapNews(
        @RequestBody @Valid RequestScrapNewsDto request,
        BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ResultDto.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build();
        }

        memberScrapNewsService.removeScrapNews(request.getNewsUrl());
        return ResultDto.builder().resultCode("200").resultMessage("스크랩 취소 완료").build();
    }
}