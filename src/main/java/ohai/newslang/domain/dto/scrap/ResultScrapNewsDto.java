package ohai.newslang.domain.dto.scrap;

import lombok.*;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResultScrapNewsDto {
    private List<ScrapNewsDto> scrapNewsList;
    private ResponsePageSourceDto pageSource;
    private RequestResult result;

    @Builder
    public ResultScrapNewsDto(List<ScrapNewsDto> scrapNewsList, ResponsePageSourceDto pageSource, RequestResult result) {
        this.scrapNewsList = scrapNewsList;
        this.pageSource = pageSource;
        this.result = result;
    }
}
