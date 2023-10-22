package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.request.ResultDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeCategoryDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeMediaDto;
import ohai.newslang.domain.dto.subscribe.subscribeReference.MediaDto;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscribe")
public class SubscribeApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final MediaService mediaService;
    private final CategoryService categoryService;
    private final TokenDecoder tokenDecoder;

    @GetMapping("/guest/media")
    public ResultSubscribeMediaDto getAllMedias() {
        List<Media> mediaList = mediaService.findAll();
        List<MediaDto> mediaDtoList = mediaList.stream()
                .map(o -> MediaDto.builder().mediaName(o.getName()).imagePath(o.getImagePath()).build())
                .collect(Collectors.toList());

        return ResultSubscribeMediaDto.builder().mediaList(mediaDtoList).result(RequestResult.builder().resultCode("200").resultMessage("").build()).build();
    }

    @GetMapping("/guest/category")
    public ResultSubscribeCategoryDto getAllCategories() {
        List<Category> subscribeItems = categoryService.findAll();
        List<String> nameList =subscribeItems.stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
        return ResultSubscribeCategoryDto.builder().nameList(nameList).result(RequestResult.builder().resultCode("200").resultMessage("").build()).build();
    }

    @GetMapping("/all")
    public ResultSubscribeDto getSubscribe(){
        Long memberId = tokenDecoder.currentUserId();
        MemberSubscribeItem memberSubscribeItem = this.memberSubscribeItemService.getMemberSubscribeItem(memberId);
        return ResultSubscribeDto.builder()
                .mediaList(memberSubscribeItem.getMemberSubscribeMediaItemList().stream().map(m -> m.getMedia().getName()).collect(Collectors.toList()))
                .categoryList(memberSubscribeItem.getSubscribeCategoryList().stream().map(c -> c.getName()).collect(Collectors.toList()))
                .keywordList(memberSubscribeItem.getSubscribeKeywordList().stream().map(k -> k.getName()).collect(Collectors.toList()))
                .result(RequestResult.builder().resultCode("200").resultMessage("").build()).build();
    }

    @PostMapping("/media")
    public ResultDto subscribeMedia(@RequestBody @Valid RequestSubscribeDto request){
        Long memberId = tokenDecoder.currentUserId();
        try {
            memberSubscribeItemService.updateSubscribeMediaItems(memberId, request.getNameList());
            return ResultDto.builder().resultCode("200").resultMessage("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().resultCode("401").resultMessage(e.getMessage()).build();
        }
    }

    @PostMapping("/category")
    public ResultDto subscribeCategory(@RequestBody @Valid RequestSubscribeDto request){
        Long memberId = tokenDecoder.currentUserId();
        try {
            memberSubscribeItemService.updateSubscribeCategory(memberId, request.getNameList());
            return ResultDto.builder().resultCode("200").resultMessage("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().resultCode("401").resultMessage(e.getMessage()).build();
        }
    }

    @PostMapping("/keyword")
    public ResultDto subscribeKeyword(@RequestBody @Valid RequestSubscribeDto request){
        Long memberId = tokenDecoder.currentUserId();
        try {
            memberSubscribeItemService.updateSubscribeKeyword(memberId, request.getNameList());
            return ResultDto.builder().resultCode("200").resultMessage("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().resultCode("401").resultMessage(e.getMessage()).build();
        }
    }
}