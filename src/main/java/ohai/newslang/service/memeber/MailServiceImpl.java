package ohai.newslang.service.memeber;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender jms;

    @Override
    public String sendMail(String mail) {
        int number = createNumber();
        jms.send(createMail(mail,number));
        return String.valueOf(number);
    }

    public int createNumber() {
        return (int) (Math.random() * (90000)) + 100000;
    }

    public MimeMessage createMail(String mail, int number) {
        MimeMessage message = jms.createMimeMessage();
        String body = "";
        body += "<h3>" + "요청하신 인증번호 입니다." + "</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>" + "인증번호란에 입력해주세요." + "</h3>";
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(mail); // 메일 수신자
            mimeMessageHelper.setSubject("인증번호"); // 메일 제목
            mimeMessageHelper.setText(body,true); // 메일 본문 내용, HTML 여부
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

}
