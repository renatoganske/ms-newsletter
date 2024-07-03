package br.com.renatoganske.email_service_ms.service;


import br.com.renatoganske.email_service_ms.dtos.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    final JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void send(EmailDto emailDto) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom(emailFrom);
            helper.setTo(emailDto.getEmailTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getContent(), true);

            emailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }
    }
}