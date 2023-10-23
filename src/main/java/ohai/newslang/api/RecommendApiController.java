package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.recommend.newsRecommend.NewsRecommendDto;
import ohai.newslang.domain.dto.recommend.opinionRecommend.OpinionRecommendDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.service.recommend.NewsRecommendService;
import ohai.newslang.service.recommend.OpinionRecommendService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommends")
public class RecommendApiController {
    private final NewsRecommendService newsRecommendService;
    private final OpinionRecommendService opinionRecommendService;

    @PostMapping("/news")
    public RequestResult changeNewsRecommend(@RequestBody NewsRecommendDto newsRecommendDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build();
        }
        return newsRecommendService.updateRecommendStatus(newsRecommendDto);
    }
    @PostMapping("/opinions")
    public RequestResult changeOpinionRecommend(@RequestBody OpinionRecommendDto opinionRecommendDto,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RequestResult.builder()
            .resultCode("202")
            .resultMessage(bindingResult.getFieldError().getDefaultMessage())
            .build();
        }
        return opinionRecommendService.updateRecommendStatus(opinionRecommendDto);
    }

}
