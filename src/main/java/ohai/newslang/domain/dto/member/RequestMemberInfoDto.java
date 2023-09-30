package ohai.newslang.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestMemberInfoDto {

    private String name;

    private String email;

    private String password;

    private String imagePath;
}
