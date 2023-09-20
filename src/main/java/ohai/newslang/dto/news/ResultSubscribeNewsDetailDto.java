package ohai.newslang.dto.news;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ohai.newslang.domain.subscribe.SubscribeSimpleNews;

@Getter
@Setter
@NoArgsConstructor()
public class ResultSubscribeNewsDetailDto {

    private SubscribeSimpleNews subscribeSimpleNews;

    public ResultSubscribeNewsDetailDto(SubscribeSimpleNews subscribeSimpleNews) {
        this.subscribeSimpleNews = subscribeSimpleNews;
    }

}
