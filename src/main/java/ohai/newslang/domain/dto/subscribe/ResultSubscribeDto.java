package ohai.newslang.domain.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeDto {

    private List<String> mediaList;
    private List<String> categoryList;
    private List<String> keywordList;
    private RequestResult result;

    @Builder
    public ResultSubscribeDto(List<String> mediaList, List<String> categoryList, List<String> keywordList, RequestResult result) {
        this.mediaList = mediaList;
        this.categoryList = categoryList;
        this.keywordList = keywordList;
        this.result = result;
    }
}