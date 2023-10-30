package ohai.newslang.domain.dto.scrap;

import lombok.Builder;
import lombok.Getter;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.enumulate.RecommendStatus;

@Getter
public class ResultScrapStatusDto {
    private boolean isScrap;
    private RequestResult result;

    @Builder
    public ResultScrapStatusDto(boolean isScrap, RequestResult result) {
        this.isScrap = isScrap;
        this.result = result;
    }
}
