package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.subscribe.MediaDetail;
import ohai.newslang.domain.RequestResult;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.Keyword;
import ohai.newslang.domain.subscribe.item.Media;
import ohai.newslang.domain.subscribe.item.SubscribeItem;
import ohai.newslang.dto.subscribe.ResultSubscribeCategoryDto;
import ohai.newslang.dto.subscribe.ResultSubscribeKeywordDto;
import ohai.newslang.dto.subscribe.ResultSubscribeMediaDto;
import ohai.newslang.service.subscribe.SubscribeItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SubscribeItemApiController {

    private final SubscribeItemService subscribeItemService;

    @GetMapping("/api/media")
    public ResultSubscribeMediaDto getAllMedias() {
        return this.getResultSubscribeMediaDto(Media.class);
    }

    @GetMapping("/api/category")
    public ResultSubscribeCategoryDto getAllCategories() {
        List<String> nameList = getSubscribeItemNameList(Category.class);
        return ResultSubscribeCategoryDto.builder().nameList(nameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }

    @GetMapping("/api/keyword")
    public ResultSubscribeKeywordDto getAllKeywords() {
        List<String> nameList = getSubscribeItemNameList(Keyword.class);
        return ResultSubscribeKeywordDto.builder().nameList(nameList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }

    private ResultSubscribeMediaDto getResultSubscribeMediaDto(Class<?> entityType){
        List<SubscribeItem> subscribeItems = subscribeItemService.findSubscribeItemList(entityType);

        List<MediaDetail> mediaDetailList = subscribeItems.stream()
                .map(o -> MediaDetail.builder().mediaName(((Media) o).getName()).mediaImagePath(((Media) o).getImagePath()).build())
                .collect(Collectors.toList());

        return ResultSubscribeMediaDto.builder().mediaList(mediaDetailList).result(RequestResult.builder().isSuccess(true).failCode("").build()).build();
    }

    private List<String> getSubscribeItemNameList(Class<?> entityType){
        List<SubscribeItem> subscribeItems = subscribeItemService.findSubscribeItemList(entityType);
        return subscribeItems.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());
    }
}
