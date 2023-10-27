package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
public class MemberInfoDto {

    private String name;
    private String loginId;
    private String email;
    private RequestResult result;

    @Builder
    public MemberInfoDto(String name, String loginId, String email, RequestResult result) {
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.result = result;
    }

}
