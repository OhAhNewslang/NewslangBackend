package ohai.newslang.repository.subscribe.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeKeywordDto {

    private int keywordCount;
    private List<String> nameList;
    private RequestResult result;

    @Builder
    public ResultSubscribeKeywordDto(List<String> nameList, RequestResult result) {
        this.keywordCount = nameList.size();
        this.nameList = nameList;
        this.result = result;
    }
}
