package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.enumulate.UserRole;

@Data
public class MemberInfoDto {

    private String name;
    private String loginId;
    private String email;
    private UserRole role;
    private RequestResult result;

    @Builder
    public MemberInfoDto(String name, String loginId, String email, UserRole role, RequestResult result) {
        this.name = name;
        this.loginId = loginId;
        this.email = email;
        this.role = role;
        this.result = result;
    }

}
