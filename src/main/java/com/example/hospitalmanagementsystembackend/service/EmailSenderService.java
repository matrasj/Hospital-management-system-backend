package com.example.hospitalmanagementsystembackend.service;

import com.example.hospitalmanagementsystembackend.model.entity.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendConfirmationAccountEmail(ConfirmationToken confirmationToken) {
        String emailContent = """
               Hi %s!
               We are happy that you're with us.
               Last step is to confirm your account.
               Please click link below to confirm your account:
               http://localhost:8081/api/v1/auth/confirmation?token=%s
               
                """.formatted(confirmationToken.getUser().getUsername(),
                confirmationToken.getToken());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(confirmationToken.getUser().getEmail());
        simpleMailMessage.setFrom("hospital.company@ttsi.com");
        simpleMailMessage.setText(emailContent);
        simpleMailMessage.setSubject("Email confirmation");

        javaMailSender.send(simpleMailMessage);
    }
}
