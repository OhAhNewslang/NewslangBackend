package ohai.newslang.service.memeber;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMailMessage;

public interface MailService {
    String sendMail(String mail);
}
