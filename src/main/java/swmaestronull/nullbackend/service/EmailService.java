package swmaestronull.nullbackend.service;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swmaestronull.nullbackend.domain.emailcode.EmailCode;
import swmaestronull.nullbackend.domain.emailcode.EmailCodeRepository;
import swmaestronull.nullbackend.domain.user.PaintUserRepository;
import swmaestronull.nullbackend.web.dto.BaseResponseDto;

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
    private final PaintUserRepository paintUserRepository;
    private final EmailCodeRepository emailCodeRepository;

    @Transactional
    public BaseResponseDto sendMessage(String receiver) throws MessagingException, UnsupportedEncodingException {
        // Todo: Test code 작성하기
        // 이미 가입한 이메일인지 체크
        if (paintUserRepository.findOneWithRoleByEmail(receiver).orElse(null) != null) {
            return new BaseResponseDto(1, "이 이메일은 이미 계정이 있습니다.", false);
        }
        String code = createCode();
        EmailCode emailCode = emailCodeRepository.findByEmail(receiver).orElse(null);
        if (emailCode != null) {
            emailCode.updateCode(code);
        } else {
            emailCodeRepository.save(EmailCode.builder()
                    .email(receiver)
                    .code(code)
                    .build());
        }
        MimeMessage message = createMessage(receiver, code);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        return new BaseResponseDto(0, "성공적으로 인증 코드를 보냈습니다.", true);
    }

    private MimeMessage createMessage(String receiver, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, receiver);
        message.setSubject("ColorAid 확인 코드 : " + code);
        String msg = "ColorAid 확인 코드는 " + code +"입니다.";
        message.setText(msg, "utf-8");
        message.setFrom(new InternetAddress("${AdminMail.id}", "ColorAid"));

        return message;
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

    @Transactional
    public BaseResponseDto checkCode(String receiver, String code) {
        EmailCode emailCode = emailCodeRepository.findByEmail(receiver)
                .orElseThrow(() -> new IllegalArgumentException("[email:" + receiver + "] code를 발송하지 않은 이메일입니다."));
        // 유효시간 판별
        if (!emailCode.isValid()) {
            return new BaseResponseDto(1, "인증 코드가 유효시간을 초과했습니다.", false);
        }
        // code 일치여부 판별
        if (!emailCode.checkCode(code)) {
            return new BaseResponseDto(1, "인증 코드가 일치하지 않습니다.", false);
        } else {
            return new BaseResponseDto(0, "인증 코드가 일치합니다.", true);
        }
    }
}
