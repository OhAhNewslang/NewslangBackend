package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.ResponseDto;

@Data
public class MemberInfoDto {
    private String name;
    private String email;
    private String imagePath;
    private ResponseDto responseDto;


    @Builder
    public MemberInfoDto(String name, String email, String imagePath, ResponseDto responseDto) {
        this.name = name;
        this.email = email;
        this.imagePath = imagePath;
        this.responseDto = responseDto;
    }

}
