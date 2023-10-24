package ohai.newslang.domain.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WithdrawPasswordDto {
    @NotBlank(message = "본인인증용 비밀번호를 입력해 주세요.")
    String password;
}
