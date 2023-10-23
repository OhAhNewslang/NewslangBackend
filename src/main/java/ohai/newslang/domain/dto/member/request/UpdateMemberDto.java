package ohai.newslang.domain.dto.member.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
public class UpdateMemberDto {

    @NotBlank(message = "이름을 입력해 주세요.")
    @Size(max = 20, min = 2, message = "이름은 최소 2글자 ~ 최대 20글자로 입력해 주세요.")
    private String name;

    @NotBlank(message = "메일 주소를 입력해 주세요.")
    @Email(message = "이메일 형식으로 입력해 주세요.")
    private String email;

    private String imagePath;

}
