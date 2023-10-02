package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.ResponseDto;

@Data
public class TokenDto{
    private String token;
    private ResponseDto responseDto;

    @Builder
    public TokenDto(String token, ResponseDto responseDto) {
        this.token = token;
        this.responseDto = responseDto;
    }
}
