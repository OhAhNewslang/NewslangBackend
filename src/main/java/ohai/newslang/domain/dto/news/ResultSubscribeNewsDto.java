package ohai.newslang.domain.dto.news;

import lombok.*;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeNewsDto {
    private List<ThumbnailNewsDto> thumbnailNewsList;
    private RequestResult result;

    @Builder
    public ResultSubscribeNewsDto(List<ThumbnailNewsDto> thumbnailNewsList, RequestResult result) {
        this.thumbnailNewsList = thumbnailNewsList;
        this.result = result;
    }
}
