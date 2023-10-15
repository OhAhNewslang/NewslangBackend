package ohai.newslang.domain.dto.page;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestPageSourceDto {

    private int page;
    private int limit;

    @Builder
    public RequestPageSourceDto(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }
}
