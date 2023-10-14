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

    // 상세 뉴스 공감순
    @GetMapping("/v2/news")
    public OpinionPagingResponseDto opinionListByLikeCountForNewsV2(
            @RequestBody OpinionPagingRequestDtoForNews opinionRequestDto){
        return opinionService.opinionListByLikeCountOrderForDetailNews(
                opinionRequestDto.getNewsUrl(),
                opinionRequestDto.getPageNumber(),
                opinionRequestDto.getPageSize());
    }

    // 상세 뉴스 최신순
    @GetMapping("/v2/news")
    public OpinionPagingResponseDto opinionListByRecentForNewsV2(
            @RequestBody OpinionPagingRequestDtoForNews opinionRequestDto){
        return opinionService.opinionListByRecentOrderForDetailNews(
                opinionRequestDto.getNewsUrl(),
                opinionRequestDto.getPageNumber(),
                opinionRequestDto.getPageSize());
    }

    // 마이페이지 공감순
    @GetMapping("/members/like")
    public OpinionPagingResponseDto opinionListByLikeCountForMemberV2(
            @RequestBody OpinionPagingRequestDto opinionRequestDto){
        return opinionService.opinionListByLikeCountOrderForMember(
                opinionRequestDto.getPageNumber(),
                opinionRequestDto.getPageSize());
    }

    // 마이페이지 최신순
    @GetMapping("/members/recent")
    public OpinionPagingResponseDto opinionListByRecentForMemberV3(
            @RequestBody OpinionPagingRequestDto opinionRequestDto){
        return opinionService.opinionListByRecentOrderForMember(
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
