package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeKeywordDto;
import ohai.newslang.domain.entity.subscribe.SubscribeKeyword;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Category;
import ohai.newslang.domain.dto.subscribe.subscribeReference.MediaDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.subscribe.subscribeReference.Media;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeCategoryDto;
import ohai.newslang.domain.dto.subscribe.ResultSubscribeMediaDto;
import ohai.newslang.service.subscribe.subscribeReference.CategoryService;
import ohai.newslang.service.subscribe.subscribeReference.MediaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SubscribeItemApiController {

    private final MediaService mediaService;
    private final CategoryService categoryService;

    @GetMapping("/api/media")
    public ResultSubscribeMediaDto getAllMedias() {
        return this.getResultSubscribeMediaDto();
    }

    @GetMapping("/api/category")
    public ResultSubscribeCategoryDto getAllCategories() {
        List<String> nameList = getSubscribeItemNameList();
        return ResultSubscribeCategoryDto.builder().nameList(nameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }

//    @GetMapping("/api/keyword")
//    public ResultSubscribeKeywordDto getAllKeywords() {
//        List<String> nameList = getSubscribeItemNameList(SubscribeKeyword.class);
//        return ResultSubscribeKeywordDto.builder().nameList(nameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
//    }

    private ResultSubscribeMediaDto getResultSubscribeMediaDto(){
        List<Media> mediaList = mediaService.findSubscribeItemList();

        List<MediaDto> mediaDtoList = mediaList.stream()
                .map(o -> MediaDto.builder().mediaName(o.getName()).mediaImagePath(o.getImagePath()).build())
                .collect(Collectors.toList());

        return ResultSubscribeMediaDto.builder().mediaList(mediaDtoList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }

    private List<String> getSubscribeItemNameList(){
        List<Category> subscribeItems = categoryService.findSubscribeItemList();
        return subscribeItems.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());
    }
}