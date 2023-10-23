package ohai.newslang.domain.dto.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordDto {
    
    @NotBlank(message = "이전 비밀번호를 입력해주세요")
    private String oldPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요")
    @Size(max = 20, min = 10, message = "새 비밀번호는 최소 10글자 ~ 최대 20글자로 입력해 주세요.")
    private String newPassword;
}
