package ohai.newslang.api;

import com.sun.mail.util.BEncoderStream;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.opinion.request.OpinionResistRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionModifyRequestDto;
import ohai.newslang.domain.dto.opinion.request.OpinionPagingRequestDtoForNews;
import ohai.newslang.domain.dto.opinion.response.ModifyOpinionResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionListResponseDto;
import ohai.newslang.domain.dto.opinion.response.OpinionResponseDto;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.opinion.OpinionService;
import org.aspectj.weaver.CompressingDataOutputStream;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/opinions")
public class OpinionApiController {
    private final OpinionService opinionService;

    @PostMapping("")
    public OpinionResponseDto resistOpinion(@RequestBody OpinionResistRequestDto opinionResistRequestDto,
                                       BindingResult bindingResult){
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
            @RequestBody OpinionPagingRequestDtoForNews opinionRequestDto,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return OpinionListResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build())
            .build();
        }

        return opinionService.opinionListByLikeCountOrderForDetailNews(
        opinionRequestDto.getNewsUrl(),
        opinionRequestDto.getPageSourceDto().getPage(),
        opinionRequestDto.getPageSourceDto().getLimit());
    }

    // 상세 뉴스 최신순
    @GetMapping("/news/recent")
    public OpinionListResponseDto opinionListByRecentForNews(
            @RequestBody OpinionPagingRequestDtoForNews opinionRequestDto,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return OpinionListResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build())
            .build();
        }

        return opinionService.opinionListByRecentOrderForDetailNews(
                opinionRequestDto.getNewsUrl(),
                opinionRequestDto.getPageSourceDto().getPage(),
                opinionRequestDto.getPageSourceDto().getLimit());
    }

    // 마이페이지 공감순
    @GetMapping("/members/like")
    public OpinionListResponseDto opinionListByLikeCountForMember(
            @RequestBody RequestPageSourceDto pageSourceDto,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return OpinionListResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build())
            .build();
        }

        return opinionService.opinionListByLikeCountOrderForMember(
                pageSourceDto.getPage(),
                pageSourceDto.getLimit());
    }

    // 마이페이지 최신순
    @GetMapping("/members/recent")
    public OpinionListResponseDto opinionListByRecentForMember(
            @RequestBody RequestPageSourceDto pageSourceDto,
            BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return OpinionListResponseDto.builder()
            .result(RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build())
            .build();
        }

        return opinionService.opinionListByRecentOrderForMember(
                pageSourceDto.getPage(),
                pageSourceDto.getLimit());
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
    public RequestResult deleteOpinion(@RequestParam("opinionId") Long opinionId){
        return opinionService.deleteOpinion(opinionId);
    }
}
