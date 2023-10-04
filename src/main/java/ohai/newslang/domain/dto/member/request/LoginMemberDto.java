package ohai.newslang.domain.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginMemberDto {
    private String loginId;

    private String password;
}