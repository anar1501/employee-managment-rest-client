package com.company.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageUtils {

    @Value("${spring.mail.username}")
    private String owningEmail;

    private final JavaMailSender javaMailSender;

    //Todo:Bunu arasdiracayig
    @Async
    public void sendAsync(String toEmail, String subject, String body) {
        new Thread(() -> sendEmail(toEmail, subject, body)).start();
    }

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(owningEmail);
        msg.setTo(toEmail);
        msg.setText(body);
        msg.setSubject(subject);
        javaMailSender.send(msg);
    }
}
