package ohai.newslang.domain.dto.news;

import lombok.*;
import ohai.newslang.domain.dto.page.ResponsePageSourceDto;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseThumbnailNewsDto {
    private List<ThumbnailNewsDto> thumbnailNewsList;
    private ResponsePageSourceDto pageSource;
    private RequestResult result;

    @Builder
    public ResponseThumbnailNewsDto(List<ThumbnailNewsDto> thumbnailNewsList, ResponsePageSourceDto pageSource, RequestResult result) {
        this.thumbnailNewsList = thumbnailNewsList;
        this.pageSource = pageSource;
        this.result = result;
    }
}
