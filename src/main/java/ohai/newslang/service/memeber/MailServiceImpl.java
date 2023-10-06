package ohai.newslang.service.memeber;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import ohai.newslang.domain.dto.member.request.CertifyDto;
import ohai.newslang.domain.dto.member.request.NewPasswordDto;
import ohai.newslang.domain.dto.member.response.FindIdDto;
import ohai.newslang.domain.dto.request.RequestResult;
import ohai.newslang.domain.entity.member.Member;
import ohai.newslang.repository.member.MemberRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final MemberRepository memberRepository;
    private final JavaMailSender jms;

    private String certificatedNumber = "";

    @Override
    @Transactional(readOnly = true)
    public RequestResult sendMail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            createNumber();
            jms.send(createMail(email));
            return RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("인증번호가 발급되었습니다.").build();
        } else {
            return RequestResult.builder()
                    .resultCode("202")
                    .resultMessage("미가입 이메일 입니다.").build();
        }

    }

    public void createNumber() {
        // 랜덤으로 인증번호 생성
        String newNumber = String.valueOf((int) (Math.random() * (90000)) + 100000);
        // 혹여나 랜덤 숫자의 자리수가 달라지거나 문자열 대입 과정에서 생길 수 있는 공백제거.
        certificatedNumber = newNumber.trim();
    }

    public MimeMessage createMail(String email) {
        MimeMessage message = jms.createMimeMessage();
        String body = "";
        body += "<h3>" + "요청하신 인증번호 입니다." + "</h3>";
        body += "<h1>" + certificatedNumber + "</h1>";
        body += "<h3>" + "인증번호란에 입력해주세요." + "</h3>";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(email); // 메일 수신자
            mimeMessageHelper.setSubject("인증번호"); // 메일 제목
            mimeMessageHelper.setText(body, true); // 메일 본문 내용, HTML 여부
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public FindIdDto checkNumberForId(CertifyDto certifyDto) {
        if (certificatedNumber.equals(certifyDto.getCertifyNumber())){
            certificatedNumber = "";
            return FindIdDto.builder()
                    .loginId(memberRepository.findLoginIdByEmail(certifyDto.getEmail()))
                    .result(RequestResult.builder()
                            .resultCode("200")
                            .resultMessage("해당 이메일로 가입된 아이디 입니다.").build()
                    ).build();
        } else {
            return FindIdDto.builder()
                    .result(RequestResult.builder()
                            .resultCode("202")
                            .resultMessage("인증번호가 틀렸습니다.").build()
                    ).build();
        }

    }

    @Override
    public RequestResult checkNumberForPassword(CertifyDto certifyDto) {
        if (certificatedNumber.equals(certifyDto.getCertifyNumber())) {
            certificatedNumber = "";
            return RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("인증 성공 비밀번호를 재설정 해주십시오.").build();
        } else {
            return RequestResult.builder().resultCode("202")
                    .resultMessage("인증번호가 틀렸습니다.")
                    .build();
        }
    }

    @Override
    @Transactional
    public RequestResult updatePassword(NewPasswordDto newPasswordDto) {
        if (newPasswordDto.getNewPassword().equals(newPasswordDto.getRepeatPassword())) {
            Member member = memberRepository.findByEmail(newPasswordDto.getEmail()).get();
            member.updatePassword(newPasswordDto.getNewPassword());
            return RequestResult.builder()
                    .resultCode("200")
                    .resultMessage("비밀번호 변경에 성공하였습니다.").build();
        } else {
            return RequestResult.builder()
                    .resultCode("202")
                    .resultMessage("비밀번호가 서로 다릅니다.").build();
        }
    }
}
