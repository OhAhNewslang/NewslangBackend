package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.opinion.request.OpinionDeleteRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionResistRequestDto;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionListResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.opinion.OpinionService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opinions")
public class OpinionApiController {
    private final OpinionService opinionService;

    @PostMapping("")
    public OpinionResponseDto resistOpinion(
            @RequestBody OpinionResistRequestDto opinionResistRequestDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return OpinionResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build()).build();
        }
        return opinionService.resistOpinion(opinionResistRequestDto);
    }

    // 상세 뉴스 공감순
    @GetMapping("/news/like")
    public OpinionListResponseDto opinionListByLikeCountForNews(
        @RequestParam("newsUrl") String newUrl,
        @RequestParam("page") int page,
        @RequestParam("limit")int limit) {

        OpinionListResponseDto rst = opinionService
                .opinionListByLikeCountOrderForDetailNews(newUrl, page, limit);

        return rst;
    }

    // 상세 뉴스 최신순
    @GetMapping("/news/recent")
    public OpinionListResponseDto opinionListByRecentForNews(
        @RequestParam("newsUrl") String newUrl,
        @RequestParam("page") int page,
        @RequestParam("limit")int limit) {

        return opinionService
        .opinionListByRecentOrderForDetailNews(newUrl, page, limit);
    }

    // 마이페이지 공감순
    @GetMapping("/members/like")
    public OpinionListResponseDto opinionListByLikeCountForMember(
        @RequestParam("page") int page,
        @RequestParam("limit")int limit){
        return opinionService
        .opinionListByLikeCountOrderForMember(page, limit);
    }

    // 마이페이지 최신순
    @GetMapping("/members/recent")
    public OpinionListResponseDto opinionListByRecentForMember(
        @RequestParam("page") int page,
        @RequestParam("limit")int limit) {
        return opinionService
        .opinionListByRecentOrderForMember(page, limit);
    }

    @PutMapping("")
    public ModifyOpinionResponseDto modifyOpinion(
            @RequestBody OpinionModifyRequestDto opinionModifyRequestDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ModifyOpinionResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage()).build())
            .build();
        }
        return opinionService.modifyContent(opinionModifyRequestDto);
    }

    @DeleteMapping("")
    public RequestResult deleteOpinion(
            @RequestBody OpinionDeleteRequestDto requestDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage()).build();
        }
        return opinionService.deleteOpinion(requestDto.getOpinionId());
    }
}
