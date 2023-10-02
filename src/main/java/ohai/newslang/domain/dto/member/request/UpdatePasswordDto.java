package ohai.newslang.domain.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDto {
    private String oldPassword;
    private String newPassword;
}
