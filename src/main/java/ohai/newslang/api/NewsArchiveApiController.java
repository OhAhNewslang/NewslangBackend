package ohai.newslang.api;

import lombok.RequiredArgsConstructor;
import ohai.newslang.dto.news.RequestSubscribeDetailNewsDto;
import ohai.newslang.dto.news.RequestSubscribeNewsDto;
import ohai.newslang.dto.news.ResultSubscribeNewsDetailDto;
import ohai.newslang.dto.news.ResultSubscribeNewsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class NewsArchiveApiController {

//    @GetMapping("/api/news/subscribe/{id}")
//    public ResultSubscribeNewsDto getSubscribeNews(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeNewsDto){
//
//    }
//
//    @GetMapping("/api/news/detail/{id}")
//    public ResultSubscribeNewsDetailDto getSubscribeDetailNews(@PathVariable("id") Long id, @RequestBody @Valid RequestSubscribeDetailNewsDto){
//
//    }
}
