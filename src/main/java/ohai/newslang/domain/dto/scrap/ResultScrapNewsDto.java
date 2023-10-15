package ohai.newslang.domain.dto.scrap;

import lombok.*;
import ohai.newslang.domain.dto.page.PageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultScrapNewsDto {
    private List<ScrapNewsDto> scrapNewsList;
    private PageSourceDto pageSource;
    private RequestResult result;

    @Builder
    public ResultScrapNewsDto(List<ScrapNewsDto> scrapNewsList, PageSourceDto pageSource, RequestResult result) {
        this.scrapNewsList = scrapNewsList;
        this.pageSource = pageSource;
        this.result = result;
    }
}
