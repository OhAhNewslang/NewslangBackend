package ohai.newslang.domain.dto.news;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class KeywordNewsDto {
    private List<ThumbnailNewsDto> thumbnailNewsList;
    private RequestResult result;

    @Builder
    public KeywordNewsDto(List<ThumbnailNewsDto> thumbnailNewsList, RequestResult result) {
        this.thumbnailNewsList = thumbnailNewsList;
        this.result = result;
    }
}
