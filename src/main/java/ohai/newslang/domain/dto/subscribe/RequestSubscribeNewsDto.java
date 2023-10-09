package ohai.newslang.domain.dto.subscribe;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestSubscribeNewsDto {
    private String loginId;
    private String postDate;
    private int limitPage;

    @Builder
    public RequestSubscribeNewsDto(String loginId, String postDate, int limitPage) {
        this.loginId = loginId;
        this.postDate = postDate;
        this.limitPage = limitPage;
    }
}
