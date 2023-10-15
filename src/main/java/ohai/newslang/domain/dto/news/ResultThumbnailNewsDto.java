package ohai.newslang.domain.dto.news;

import lombok.*;
import ohai.newslang.domain.dto.page.PageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultThumbnailNewsDto {
    private List<ThumbnailNewsDto> thumbnailNewsList;
    private PageSourceDto pageSource;
    private RequestResult result;

    @Builder
    public ResultThumbnailNewsDto(List<ThumbnailNewsDto> thumbnailNewsList, PageSourceDto pageSource, RequestResult result) {
        this.thumbnailNewsList = thumbnailNewsList;
        this.pageSource = pageSource;
        this.result = result;
    }
}
