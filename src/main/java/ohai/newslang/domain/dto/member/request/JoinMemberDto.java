package ohai.newslang.domain.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinMemberDto {
    private String name;

    private String loginId;

    private String email;

    private String password;

}
