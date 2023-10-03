package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
public class TokenDto{
    private String token;
    private RequestResult result;

    @Builder
    public TokenDto(String token, RequestResult result) {
        this.token = token;
        this.result = result;
    }
}
