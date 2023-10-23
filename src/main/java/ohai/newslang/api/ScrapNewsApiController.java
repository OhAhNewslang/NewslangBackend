package ohai.newslang.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;
import ohai.newslang.domain.dto.request.ResultDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.scrap.*;
import ohai.newslang.service.scrap.MemberScrapNewsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scrap")
public class ScrapNewsApiController {

    private final MemberScrapNewsService memberScrapNewsService;
    private final TokenDecoder tokenDecoder;


    @PostMapping("/news")
    public ResultDto scrapNews(@RequestBody @Valid RequestScrapNewsDto request,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultDto.builder()
                    .resultCode("202")
                    .resultMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();
        }
            memberScrapNewsService.addScrapNews(request.getNewsUrl());
            return ResultDto.builder().resultMessage("뉴스 스크랩 성공").resultCode("200").build();
    }

    @GetMapping("/news")
    public ResultScrapNewsDto getScrapNews(@RequestBody @Valid RequestPageSourceDto pageSourceDto,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultScrapNewsDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build()).build();
        }

        return memberScrapNewsService.scarpNewsList(pageSourceDto.getPage(), pageSourceDto.getLimit());
    }

    // + a 여러개 선택해서 배열로 받아서 스크랩 취소하기

    @DeleteMapping("/news")
    public ResultDto removeScrapNews(@RequestBody @Valid RequestScrapNewsDto request,
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