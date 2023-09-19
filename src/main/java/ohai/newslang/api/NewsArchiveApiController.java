package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.News;
import ohai.newslang.domain.NewsArchive;
import ohai.newslang.domain.subscribe.SubscribeSimpleNews;
import ohai.newslang.domain.subscribe.item.Category;
import ohai.newslang.domain.subscribe.item.Keyword;
import ohai.newslang.domain.subscribe.item.Media;
import ohai.newslang.dto.news.ResultSubscribeNewsDto;
import ohai.newslang.service.NewsArchiveService;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NewsArchiveApiController {

    private final MemberSubscribeItemService memberSubscribeItemService;
    private final NewsArchiveService newsArchiveService;

    @GetMapping("/api/news/subscribe/{id}")
    public ResultSubscribeNewsDto getSubscribeNews(@PathVariable("id") Long id) {
        List<String> mediaNameList = this.memberSubscribeItemService.findSubscribeNameList(id, Media.class);
        List<String> categoryNameList = this.memberSubscribeItemService.findSubscribeNameList(id, Category.class);
        List<String> keywordNameList = this.memberSubscribeItemService.findSubscribeNameList(id, Keyword.class);
        List<NewsArchive> newsArchiveList = newsArchiveService.findByNameList(mediaNameList, categoryNameList, keywordNameList);

        List<SubscribeSimpleNews> collect = newsArchiveList.stream()
                .map(n -> {
                    News news = n.getNews();
                    return new SubscribeSimpleNews(news.getUrl(), news.getMediaName(), news.getCategoryName(), news.getTitle(), news.getContents());
                })
                .collect(Collectors.toList());

        return new ResultSubscribeNewsDto(collect);
    }
//
//    @GetMapping("/api/news/detail/{id}")
//    public ResultSubscribeNewsDetailDto getSubscribeDetailNews(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDetailNewsDto){
//
//    }
}
