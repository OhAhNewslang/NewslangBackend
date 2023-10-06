package ohai.newslang.domain.dto.member.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertifyDto {
    private String email;

    private String certifyNumber;
}
