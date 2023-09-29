package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.RequestResult;
import ohai.newslang.domain.dto.subscribe.RequestSubscribeDto;
import ohai.newslang.domain.dto.subscribe.ResultDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeDto;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;

    @PostMapping("/api/media/{id}")
    public ResultDto subscribeMedia(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        try {
            Long memberSubscribeId = memberSubscribeItemService.updateSubscribeMedias(id, request.getNameList());
            return ResultDto.builder().isSuccess(true).failCode("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().isSuccess(false).failCode("").build();
        }
    }

    @GetMapping("/api/media/{id}")
    public ResultSubscribeDto getMedias(@PathVariable("id") Long id){
        try {
            List<String> subscribeNameList = this.memberSubscribeItemService.findSubscribeMediaNameList(id);
            return ResultSubscribeDto.builder().memberId(id).subscribeList(subscribeNameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
        } catch (Exception e){
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultSubscribeDto.builder().memberId(id).subscribeList(null).result(RequestResult.builder().isSuccess(false).failCode("").build()).build();
        }
    }

    @PostMapping("/api/category/{id}")
    public ResultDto subscribeCategory(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        try {
            Long memberSubscribeId = memberSubscribeItemService.updateSubscribeCategory(id, request.getNameList());
            return ResultDto.builder().isSuccess(true).failCode("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().isSuccess(false).failCode("").build();
        }
    }

    @GetMapping("/api/category/{id}")
    public ResultSubscribeDto getCategory(@PathVariable("id") Long id){
        try {
            List<String> subscribeNameList = this.memberSubscribeItemService.findCategoryNameList(id);
            return ResultSubscribeDto.builder().memberId(id).subscribeList(subscribeNameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
        } catch (Exception e){
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultSubscribeDto.builder().memberId(id).subscribeList(null).result(RequestResult.builder().isSuccess(false).failCode("").build()).build();
        }
    }

    @PostMapping("/api/keyword/{id}")
    public ResultDto subscribeKeyword(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        try {
            Long memberSubscribeId = memberSubscribeItemService.updateSubscribeKeyword(id, request.getNameList());
            return ResultDto.builder().isSuccess(true).failCode("").build();
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().isSuccess(false).failCode("").build();
        }
    }

    @GetMapping("/api/keyword/{id}")
    public ResultSubscribeDto getKeywords(@PathVariable("id") Long id){
        try {
            List<String> subscribeNameList = this.memberSubscribeItemService.findKeywordNameList(id);
            return ResultSubscribeDto.builder().memberId(id).subscribeList(subscribeNameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
        } catch (Exception e){
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultSubscribeDto.builder().memberId(id).subscribeList(null).result(RequestResult.builder().isSuccess(false).failCode("").build()).build();
        }
    }
}