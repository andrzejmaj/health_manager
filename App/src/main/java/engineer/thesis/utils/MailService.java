package engineer.thesis.utils;


import engineer.thesis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Locale;

@Component
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) {
        String url = "http://localhost:8080/changePassword?id=" +
                user.getId() + "&token=" + token;
        String message = "Your password has been reset.\n Please click on link below to set your new password.";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(new String[]{"andrzejmaj123@gmail.com", "l.cynarski@gmail.com", "kmacieslik@gmail.com"});
        email.setFrom("sender");
        return email;
    }

    public void send(SimpleMailMessage message) {
        System.out.println("Sending: " + message);
        mailSender.send(message);
    }
}
