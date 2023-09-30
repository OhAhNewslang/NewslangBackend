package ohai.newslang.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMemberInfoDto {

    private String name;

    private String email;

    private String password;

    private String imagePath;
}
