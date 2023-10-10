//package ohai.newslang.api;
//
//import lombok.RequiredArgsConstructor;
//import ohai.newslang.domain.dto.recommend.newsRecommend.NewsRecommendDto;
//import ohai.newslang.domain.dto.recommend.opinionRecommend.OpinionRecommendDto;
//import ohai.newslang.domain.dto.request.RequestResult;
//import ohai.newslang.service.recommend.NewsRecommendService;
//import ohai.newslang.service.recommend.OpinionRecommendService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/recommend")
//public class RecommendApiController {
//    private final NewsRecommendService newsRecommendService;
//    private final OpinionRecommendService opinionRecommendService;
//
//    @PostMapping("/news")
//    public RequestResult changeNewsRecommend(NewsRecommendDto newsRecommendDto) {
//        return newsRecommendService.updateRecommendStatus(newsRecommendDto);
//    }
//    @PostMapping("/opinion")
//    public RequestResult changeOpinionRecommend(OpinionRecommendDto opinionRecommendDto) {
//        return opinionRecommendService.updateRecommendStatus(opinionRecommendDto);
//    }
//
//}
