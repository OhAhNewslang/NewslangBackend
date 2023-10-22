package ohai.newslang.domain.dto.subscribe;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.page.RequestPageSourceDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestSubscribeNewsDto {
    private RequestPageSourceDto pageSource;

    @Builder
    public RequestSubscribeNewsDto(RequestPageSourceDto pageSource) {
        this.pageSource = pageSource;
    }
}
