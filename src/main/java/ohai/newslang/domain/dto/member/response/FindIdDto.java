package ohai.newslang.domain.dto.member.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ohai.newslang.domain.dto.request.RequestResult;

@Data
@Builder
@AllArgsConstructor
public class FindIdDto {

    String loginId;
    RequestResult result;

}
