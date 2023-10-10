//package ohai.newslang.api;
//
//import lombok.RequiredArgsConstructor;
//import ohai.newslang.domain.dto.opinion.request.OpinionCreateRequestDto;
//import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
//import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
//import ohai.newslang.domain.dto.request.RequestResult;
//import ohai.newslang.service.opinion.OpinionService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/opinion")
//public class OpinionApiController {
//    private final OpinionService opinionService;
//
//    @PostMapping("")
//    public RequestResult resistOpinion(@RequestBody OpinionCreateRequestDto opinionCreateRequestDto){
//        return opinionService.resistOpinion(opinionCreateRequestDto);
//    }
//
//    @GetMapping("/member")
//    public List<OpinionResponseDto> opinionListByNews(@RequestParam("newsId") Long newsId){
//        return opinionService.opinionListByDetailNews(newsId);
//    }
//
//    @GetMapping("/news")
//    public List<OpinionResponseDto> opinionListByMember(){
//        return opinionService.opinionListByMember();
//    }
//
//    @PutMapping("")
//    public OpinionResponseDto modifyOpinion(@RequestBody OpinionModifyRequestDto opinionModifyRequestDto) {
//        return opinionService.modifyContent(opinionModifyRequestDto);
//    }
//
//    @DeleteMapping("")
//    public RequestResult deleteOpinion(@RequestParam("opinionId") Long opinionId){
//        return opinionService.deleteOpinion(opinionId);
//    }
//}
