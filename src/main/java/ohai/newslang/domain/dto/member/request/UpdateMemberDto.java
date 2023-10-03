package ohai.newslang.domain.dto.member.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ohai.newslang.domain.dto.request.ResponseDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberDto {
    private String name;
    private String email;
    private String imagePath;
}
