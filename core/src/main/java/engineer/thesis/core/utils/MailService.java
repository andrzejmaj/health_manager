package engineer.thesis.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, Long id, String email) {
        String url = "http://localhost:3000/#/changePassword?id=" +
                id + "&token=" + token;
        String message = "Your password has been reset.\n Please click on link below to set your new password.";
        return constructEmail("Reset Password", message + " \r\n" + url, email);
    }

    public SimpleMailMessage constuctUserCreationEmail(String email, String password) {
        String body = "Your account has been created.\n To access please use your generated password: " + password + ". You will be asked to change password once first login attempt.";
        return constructEmail("Welcome to the Health Manager application", body, email);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             String sendTo) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(new String[]{sendTo});
        email.setFrom("sender");
        return email;
    }

    public void send(SimpleMailMessage message) {
        System.out.println("Sending: " + message);
        mailSender.send(message);
    }
}
