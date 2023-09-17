package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.RequestResult;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.Keyword;
import ohai.newslang.domain.subscribe.item.Media;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.repository.subscribe.dto.RequestSubscribeDto;
import ohai.newslang.repository.subscribe.dto.ResultDto;
import ohai.newslang.repository.subscribe.dto.ResultSubscribeDto;
import ohai.newslang.service.subscribe.SubscribeItemService;
import ohai.newslang.service.subscribe.MemberSubscribeService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SubscribeApiController {

    private final MemberSubscribeService memberSubscribeService;
    private final SubscribeItemService subscribeItemService;

    @PostMapping("/api/media/{id}")
    public ResultDto subscribeMedia(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        return this.postSubscribeDto(id, request, Media.class);
    }

    @GetMapping("/api/media/{id}")
    public ResultSubscribeDto getMedias(@PathVariable("id") Long id){
        return this.getSubscribeDto(id, Media.class);
    }

    @PostMapping("/api/category/{id}")
    public ResultDto subscribeCategory(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        return this.postSubscribeDto(id, request, Category.class);
    }

    @GetMapping("/api/category/{id}")
    public ResultSubscribeDto getCategory(@PathVariable("id") Long id){
        return this.getSubscribeDto(id, Category.class);
    }

    @PostMapping("/api/keyword/{id}")
    public ResultDto subscribeKeyword(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDto request){
        List<String> makeNameList = subscribeItemService.findNotExistNameList(request.getNameList(), Keyword.class);
        List<SubscribeItem> makeItems = makeNameList.stream()
                .map(o -> {
                    Keyword key = new Keyword();
                    key.setName(o);
                    return key; })
                .collect(Collectors.toList());
        if (makeItems.size() > 0)
            subscribeItemService.saveSubscribeItem(makeItems);
        return this.postSubscribeDto(id, request, Keyword.class);
    }

    @GetMapping("/api/keyword/{id}")
    public ResultSubscribeDto getKeywords(@PathVariable("id") Long id){
        return this.getSubscribeDto(id, Keyword.class);
    }

    private ResultDto postSubscribeDto(Long id, RequestSubscribeDto request, Class<?> entityType){
        try {
            memberSubscribeService.updateSubscribe(id, request.getNameList(), entityType);
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultDto.builder().isSuccess(false).failCode("").build();
        }
        return ResultDto.builder().isSuccess(true).failCode("").build();
    }

    private ResultSubscribeDto getSubscribeDto(Long id, Class<?> entityType){
        List<String> subscribeList = new ArrayList<>();
        try {
            List<String> subscribeNameList = memberSubscribeService.findSubscribeList(id, entityType);
            subscribeList.addAll(subscribeNameList);
        } catch (Exception e) {
            // to do list finder
            // 1. fail 코드 정의 필요
            // 2. Exception 재정의 필요
            // 3. Exception 종류에 따라(기존 데이터 삭제 후 Exception 등), 문제 발생시 롤백 처리 필요
            return ResultSubscribeDto.builder().memberId(id).subscribeList(null).result(RequestResult.builder().isSuccess(false).failCode("").build()).build();
        }
        return ResultSubscribeDto.builder().memberId(id).subscribeList(subscribeList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }
}
