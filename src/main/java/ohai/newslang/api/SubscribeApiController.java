package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.configuration.jwt.TokenDecoder;
import ohai.newslang.domain.dto.request.ResultDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeCategoryDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeMediaDto;
import ohai.newslang.domain.entity.subscribe.MemberSubscribeItem;
import ohai.newslang.domain.entity.subscribe.SubscribeCategory;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

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

        return ResultSubscribeMediaDto.builder()
        .mediaList(mediaService.findAll())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("언론사 목록 조회 성공").build())
        .build();
    }

    @GetMapping("/guest/category")
    public ResultSubscribeCategoryDto getAllCategories() {
        return ResultSubscribeCategoryDto.builder()
        .nameList(categoryService.findAll())
        .result(RequestResult.builder()
        .resultCode("200")
        .resultMessage("카테고리 목록 조회 성공").build())
        .build();
    }

    @GetMapping("/all")
    public ResultSubscribeDto getSubscribe(){
        MemberSubscribeItem memberSubscribeItem = memberSubscribeItemService
        .getMemberSubscribeItem();
        return ResultSubscribeDto.builder()
        .mediaList(memberSubscribeItem.getMemberSubscribeMediaItemList().stream()
        .map(m -> m.getMedia().getName()).collect(Collectors.toList()))
        .categoryList(memberSubscribeItem.getSubscribeCategoryList().stream()
        .map(SubscribeCategory::getName).collect(Collectors.toList()))
        .keywordList(memberSubscribeItem.getSubscribeKeywordList().stream()
        .map(SubscribeKeyword::getName).collect(Collectors.toList())).build();
    }

    @PostMapping("/media")
    public RequestResult subscribeMedia(
        @RequestBody @Valid RequestSubscribeDto request){
        return memberSubscribeItemService
        .updateSubscribeMediaItems(request.getNameList());
    }

    @PostMapping("/category")
    public RequestResult subscribeCategory(
        @RequestBody @Valid RequestSubscribeDto request){
        return memberSubscribeItemService
        .updateSubscribeCategory(request.getNameList());
    }

    @PostMapping("/keyword")
    public ResultDto subscribeKeyword(
        @RequestBody @Valid RequestSubscribeDto request){
        memberSubscribeItemService.updateSubscribeKeyword(request.getNameList());
        return ResultDto.builder()
        .resultCode("200")
        .resultMessage("임시").build();
    }
}