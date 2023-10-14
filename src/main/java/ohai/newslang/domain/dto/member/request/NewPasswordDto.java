package ohai.newslang.domain.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewPasswordDto {
    private String email;
    private String newPassword;
    private String repeatPassword;
}
