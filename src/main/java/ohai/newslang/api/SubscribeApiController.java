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
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
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
public class SubscribeApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final MediaService mediaService;
    private final CategoryService categoryService;
    private final TokenDecoder tokenDecoder;

    @GetMapping("/api/media")
    public ResultSubscribeMediaDto getAllMedias() {

        return ResultSubscribeMediaDto.builder()
        .mediaList(mediaService.findAll())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("언론사 목록 조회 성공").build())
        .build();
    }

    @GetMapping("/api/category")
    public ResultSubscribeCategoryDto getAllCategories() {
        return ResultSubscribeCategoryDto.builder()
        .nameList(categoryService.findAll())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("카테고리 목록 조회 성공").build())
        .build();
    }

    @GetMapping("/api/subscribe")
    public ResultSubscribeDto getSubscribe(){
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemService.getMemberSubscribeItem();
        return ResultSubscribeDto.builder()
        .mediaList(memberSubscribeItem.getMemberSubscribeMediaItemList().stream()
        .map(m -> m.getMedia().getName()).collect(Collectors.toList()))
        .categoryList(memberSubscribeItem.getSubscribeCategoryList().stream()
        .map(SubscribeCategory::getName).collect(Collectors.toList()))
        .keywordList(memberSubscribeItem.getSubscribeKeywordList().stream()
        .map(SubscribeKeyword::getName).collect(Collectors.toList())).build();
    }

    @PostMapping("/api/media")
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

    @PostMapping("/api/category")
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

    @PostMapping("/api/keyword")
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