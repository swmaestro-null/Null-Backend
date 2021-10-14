package swmaestronull.nullbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@AllArgsConstructor
@PropertySource("classpath:application-email.properties")
public class EmailService {

    private final JavaMailSender emailSender;

    private MimeMessage createMessage(String receiver) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();

        String code = createCode();
        message.addRecipients(Message.RecipientType.TO, receiver);
        message.setSubject("ColorAid 확인 코드 : " + code);
        String msg = "ColorAid 확인 코드는 " + code +"입니다.";
        message.setText(msg, "utf-8");
        message.setFrom(new InternetAddress("${AdminMail.id}", "ColorAid"));

        return message;
    }

    public void sendMessage(String receiver) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = createMessage(receiver);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    private String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 6; i++) {
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char)((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char)((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }
}
