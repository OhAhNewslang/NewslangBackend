package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResultDetailNewsDto {

    private DetailNewsDto detailNews;
    private RequestResult result;

    @Builder
    public ResultDetailNewsDto(DetailNewsDto detailNews, RequestResult result) {
        this.detailNews = detailNews;
        this.result = result;
    }
}
