package ohai.newslang.service.memeber;

import ohai.newslang.domain.dto.member.request.CertifyDto;
import ohai.newslang.domain.dto.member.request.NewPasswordDto;
import ohai.newslang.domain.dto.member.response.FindIdDto;
import ohai.newslang.domain.dto.request.RequestResult;

public interface MailService {
    RequestResult sendMail(String mail);
    FindIdDto checkCodeForId(CertifyDto certifyDto);
    RequestResult checkCodeForPassword(CertifyDto certifyDto);
    RequestResult updatePassword(NewPasswordDto newPasswordDto);
}
