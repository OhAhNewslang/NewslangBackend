package ohai.newslang.dto.news;

import lombok.*;
import ohai.newslang.domain.subscribe.SubscribeSimpleNews;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultSubscribeNewsDto {
    private List<SubscribeSimpleNews> subscribeNews;

    public ResultSubscribeNewsDto(List<SubscribeSimpleNews> subscribeSimpleNewsList) {
        this.subscribeNews = subscribeSimpleNewsList;
    }
}
