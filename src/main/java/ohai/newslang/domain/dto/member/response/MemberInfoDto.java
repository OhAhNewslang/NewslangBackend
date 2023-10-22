package ohai.newslang.domain.dto.member.response;

import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
public class MemberInfoDto {

    private String name;
    private String email;
    private String imagePath;
    private RequestResult result;

    @Builder
    public MemberInfoDto(String name, String email, String imagePath, RequestResult result) {
        this.name = name;
        this.email = email;
        this.imagePath = imagePath;
        this.result = result;
    }

}
