package ohai.newslang.api;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionPagingRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionPagingRequestDtoForNews;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionPagingResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.opinion.OpinionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opinions")
public class OpinionApiController {
    private final OpinionService opinionService;

    @PostMapping("")
    public RequestResult resistOpinion(@RequestBody OpinionCreateRequestDto opinionCreateRequestDto){
        return opinionService.resistOpinion(opinionCreateRequestDto);
    }

    // v0 -> 정렬 순서 좋아요 DESC
    @GetMapping("/news")
    public OpinionPagingResponseDto opinionListByLikeCountForNews(
            @RequestParam("newsUrl") String  newsUrl){
        return opinionService.opinionListByLikeCountOrderForDetailNews(
                newsUrl,0,3);
    }

    // v1 정렬 순서 -> 등록 날짜 DESC(최신순)
    @GetMapping("/v1/news")
    public OpinionPagingResponseDto opinionListByRecentForNewsV1(
            @RequestParam("newsUrl") String newsUrl){
        return opinionService.opinionListByRecentOrderForDetailNews(
                newsUrl,0,3);
    }

    // v2 페이징 관련 정보를 JSON으로 받아서 사용, 정렬 순서는 v0를 따름
    @GetMapping("/v2/news")
    public OpinionPagingResponseDto opinionListByLikeCountForNewsV2(
            @RequestBody OpinionPagingRequestDtoForNews opinionRequestDto){
        return opinionService.opinionListByLikeCountOrderForDetailNews(
                opinionRequestDto.getNewsUrl(),
                opinionRequestDto.getPageNumber(),
                opinionRequestDto.getPageSize());
    }

    // v0 -> 정렬 순서 좋아요 DESC
    @GetMapping("/members")
    public OpinionPagingResponseDto opinionListByLikeCountForMember(){
        return opinionService.opinionListByLikeCountOrderForMember(
                0,3);
    }

    // v1 정렬 순서 -> 등록 날짜 DESC(최신순)
    @GetMapping("/v1/members")
    public OpinionPagingResponseDto opinionListByRecentForMemberV1(){
        return opinionService.opinionListByRecentOrderForMember(
                0,3);
    }

    // v2 페이징 관련 정보를 받아서 사용, 정렬 순서는 v0를 따름
    @GetMapping("/v2/members")
    public OpinionPagingResponseDto opinionListByLikeCountForMemberV2(
            @RequestBody OpinionPagingRequestDto opinionRequestDto){
        return opinionService.opinionListByLikeCountOrderForMember(
                opinionRequestDto.getPageNumber(),
                opinionRequestDto.getPageSize());
    }

    @PutMapping("")
    public ModifyOpinionResponseDto modifyOpinion(
            @RequestBody OpinionModifyRequestDto opinionModifyRequestDto) {
        return opinionService.modifyContent(opinionModifyRequestDto);
    }

    @DeleteMapping("")
    public RequestResult deleteOpinion(@RequestParam("opinionId") Long opinionId){
        return opinionService.deleteOpinion(opinionId);
    }
}
