package ohai.newslang.domain.dto.member.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinMemberDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    @Size(max = 20, min = 2, message = "이름은 최소 2글자 ~ 최대 20글자로 입력해 주세요.")
    private String name;

    @NotBlank(message = "아이디를 입력해 주세요")
    @Size(max = 20, min = 8, message = "아이디는 최소 8글자 ~ 최대 20글자로 입력해 주세요.")
    private String loginId;

    @NotBlank(message = "메일 주소를 입력해 주세요.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;

    @NotBlank(message = "비밀 번호를 입력해 주세요.")
    @Size(max = 20, min = 10, message = "비밀번호는 최소 10글자 ~ 최대 20글자로 입력해 주세요.")
    private String password;

}
