package ohai.newslang.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.RequestResult;

import java.util.List;

@Getter
@NoArgsConstructor
public class ResultSubscribeCategoryDto {

    private int categoryCount;
    private List<String> nameList;
    private RequestResult result;

    @Builder
    public ResultSubscribeCategoryDto(List<String> nameList, RequestResult result) {
        this.categoryCount = nameList.size();
        this.nameList = nameList;
        this.result = result;
    }
}
