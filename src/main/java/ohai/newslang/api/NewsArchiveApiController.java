package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.entity.news.News;
import ohai.newslang.domain.entity.news.NewsArchive;
import ohai.newslang.domain.dto.subscribe.SubscribeSimpleNews;
import ohai.newslang.domain.dto.news.ResultSubscribeNewsDetailDto;
import ohai.newslang.domain.dto.news.ResultSubscribeNewsDto;
import ohai.newslang.service.crawling.NewsArchiveService;
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
        List<String> mediaNameList = this.memberSubscribeItemService.findSubscribeMediaNameList(id);
        List<String> categoryNameList = this.memberSubscribeItemService.findCategoryNameList(id);
        List<String> keywordNameList = this.memberSubscribeItemService.findKeywordNameList(id);
        List<NewsArchive> newsArchiveList = newsArchiveService.findByNameList(mediaNameList, categoryNameList, keywordNameList);

        List<SubscribeSimpleNews> collect = newsArchiveList.stream()
                .map(n -> {
                    News news = n.getNews();
                    return SubscribeSimpleNews.builder().url(news.getUrl()).mediaName(news.getMediaName()).categoryName(news.getCategoryName()).title(news.getTitle()).contents(news.getContents()).thumbnailImagePath(news.getThumbnailImagePath()).build();
                })
                .collect(Collectors.toList());

        return new ResultSubscribeNewsDto(collect);
    }

    @GetMapping("/api/news/detail/{url}")
    public ResultSubscribeNewsDetailDto getSubscribeDetailNews(@PathVariable("url") String url){
        News news = newsArchiveService.findByUrl(url).getNews();
        return new ResultSubscribeNewsDetailDto(SubscribeSimpleNews.builder().url(news.getUrl()).mediaName(news.getMediaName()).categoryName(news.getCategoryName()).title(news.getTitle()).contents(news.getContents()).thumbnailImagePath(news.getThumbnailImagePath()).build());
    }
}