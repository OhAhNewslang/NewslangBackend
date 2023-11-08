package ohai.newslang.domain.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.enumulate.SubscribeStatus;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeDto {

    private List<String> mediaList;
    private List<String> categoryList;
    private List<String> keywordList;
    private String mediaSubscribeStatus;
    private String categorySubscribeStatus;
    private String keywordSubscribeStatus;
    private RequestResult result;

    @Builder
    public ResultSubscribeDto(List<String> mediaList, List<String> categoryList, List<String> keywordList, SubscribeStatus mediaSubscribeStatus, SubscribeStatus categorySubscribeStatus, SubscribeStatus keywordSubscribeStatus, RequestResult result) {
        this.mediaList = mediaList;
        this.categoryList = categoryList;
        this.keywordList = keywordList;
        this.mediaSubscribeStatus = String.valueOf(mediaSubscribeStatus);
        this.categorySubscribeStatus = String.valueOf(categorySubscribeStatus);
        this.keywordSubscribeStatus = String.valueOf(keywordSubscribeStatus);
        this.result = result;
    }
}