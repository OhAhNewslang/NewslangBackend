package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.opinion.OpinionService;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opinions")
public class OpinionApiController {
    private final OpinionService opinionService;

    @PostMapping("")
    public RequestResult resistOpinion(@RequestBody OpinionCreateRequestDto opinionCreateRequestDto){
        return opinionService.resistOpinion(opinionCreateRequestDto);
    }

    @GetMapping("/members")
    public Slice<OpinionResponseDto> opinionListByNews(@RequestParam("newsId") Long newsId){
        return opinionService.opinionListByDetailNews(newsId);
    } // 페이징 관련 디티오 적용 예정

    @GetMapping("/news")
    public Slice<OpinionResponseDto> opinionListByMember(){
        return opinionService.opinionListByMember();
    } // 페이징 관련 디티오 적용 예정

    @PutMapping("")
    public OpinionResponseDto modifyOpinion(@RequestBody OpinionModifyRequestDto opinionModifyRequestDto) {
        return opinionService.modifyContent(opinionModifyRequestDto);
    }

    @DeleteMapping("")
    public RequestResult deleteOpinion(@RequestParam("opinionId") Long opinionId){
        return opinionService.deleteOpinion(opinionId);
    }
}
