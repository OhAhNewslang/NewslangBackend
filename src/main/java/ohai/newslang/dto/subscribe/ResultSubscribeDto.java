package ohai.newslang.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeDto {

    private Long memberId;
    private int subscribeCount;
    private List<String> subscribeList;
    private RequestResult result;

    @Builder
    public ResultSubscribeDto(Long memberId, List<String> subscribeList, RequestResult result) {
        this.memberId = memberId;
        this.subscribeCount = subscribeList.size();
        this.subscribeList = subscribeList;
        this.result = result;
    }
}