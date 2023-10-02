package ohai.newslang.domain.dto.member;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.ResponseDto;

@Data
public class MemberInfoDto {
    private String name;
    // 이메일이 아이디이고 곧 후보키 이므로 변경 불가능
    private final String email;
    private String imagePath;
    private ResponseDto responseDto;


    @Builder
    public MemberInfoDto(String name, String email, String imagePath, ResponseDto responseDto) {
        this.responseDto = responseDto;
        this.name = name;
        this.email = email;
        this.imagePath = imagePath;
    }

}
