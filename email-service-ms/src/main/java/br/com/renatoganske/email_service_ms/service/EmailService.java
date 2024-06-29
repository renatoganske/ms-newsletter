package br.com.renatoganske.email_service_ms.service;

import br.com.renatoganske.email_service_ms.dtos.EmailDto;
import br.com.renatoganske.email_service_ms.dtos.ReceivedEmailDto;
import br.com.renatoganske.email_service_ms.dtos.RecipientDto;
import br.com.renatoganske.email_service_ms.entities.Email;
import br.com.renatoganske.email_service_ms.enums.StatusEmail;
import br.com.renatoganske.email_service_ms.repositories.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {

    final EmailRepository emailRepository;

    final JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    public List<Email> send(ReceivedEmailDto emailDto) {
        List<Email> savedEmails = new ArrayList<>();
        try {
            for (RecipientDto recipient : emailDto.recipients()) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

                helper.setFrom(emailFrom);
                helper.setTo(recipient.email());
                helper.setSubject(emailDto.template().subject());
                helper.setText(emailDto.template().content(), true);

                emailSender.send(mimeMessage);

                Email email = Email.builder()
                        .emailTemplateId(emailDto.template().id())
                        .emailFrom(emailFrom)
                        .emailTo(recipient.email())
                        .emailName(emailDto.template().name())
                        .subject(emailDto.template().subject())
                        .content(emailDto.template().content())
                        .sendDateEmail(LocalDateTime.now())
                        .recipientId(recipient.id())
                        .recipientName(recipient.name())
                        .build();


                savedEmails.add(emailRepository.save(email));
            }
        } catch (MailException | MessagingException e) {
            for (Email email : savedEmails) {
                email.setStatusEmail(StatusEmail.ERROR);
                emailRepository.save(email);
            }
        }
        return savedEmails;
    }
}