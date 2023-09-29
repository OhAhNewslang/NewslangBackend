package ohai.newslang.domain.dto.scrap;

import lombok.*;
import ohai.newslang.domain.dto.RequestResult;
import ohai.newslang.domain.dto.ScrapNews;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultScrapNewsDto {
    private Long memberId;
    private List<ScrapNews> scrapNewsList;
    private RequestResult result;

    @Builder
    public ResultScrapNewsDto(Long memberId, List<ScrapNews> scrapNewsList, RequestResult result) {
        this.memberId = memberId;
        this.scrapNewsList = scrapNewsList;
        this.result = result;
    }
}
