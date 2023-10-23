package ohai.newslang.domain.dto.page;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePageSourceDto {

    private int page;
    private int limit;
    private int totalPage;

    @Builder
    public ResponsePageSourceDto(int page, int limit, int totalPage) {
        this.page = page;
        this.limit = limit;
        this.totalPage = totalPage;
    }
}
