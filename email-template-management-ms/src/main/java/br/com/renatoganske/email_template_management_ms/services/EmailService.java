package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.*;
import br.com.renatoganske.email_template_management_ms.entities.Email;
import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import br.com.renatoganske.email_template_management_ms.errorHandling.exception.business.EmailNotFoundException;
import br.com.renatoganske.email_template_management_ms.errorHandling.exception.business.EmailTemplateNotFoundException;
import br.com.renatoganske.email_template_management_ms.errorHandling.exception.business.RecipientNotFoundException;
import br.com.renatoganske.email_template_management_ms.repositories.EmailRepository;
import br.com.renatoganske.email_template_management_ms.repositories.EmailTemplateRepository;
import br.com.renatoganske.email_template_management_ms.repositories.RecipientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final RecipientRepository recipientRepository;
    private final EmailSenderService emailSenderService;

    public List<ToListEmailDto> getAll() {
        log.info("Getting all emails");
        return emailRepository.findAll().stream().map(Email::toListEmailDto).toList();
    }


    public Email getById(UUID id) {
        log.info("Getting email with id {}", id);
        Optional<Email> email = getEmail(id);
        if (email.isPresent()) {
            return email.get();
        }
        log.error("Email not found with id {}", id);
        throw new EmailNotFoundException();
    }


    public Email save(EmailOnlyIdDto emailOnlyIdDto) {
        log.info("Saving email");
        Email savedEmail = emailRepository.save(emailOnlyIdDto.toEntity());
        log.info("Email with id {} saved", savedEmail.getId());
        return savedEmail;
    }


    public Email update(UUID id, EmailOnlyIdDto updatingEmailOnlyIdDto) {
        log.info("Updating email with id {}", id);

        Optional<Email> optionalEmail = getEmail(id);
        if (optionalEmail.isPresent()) {

            Email email = optionalEmail.get();
            UUID templateId = updatingEmailOnlyIdDto.template().id();
            EmailTemplate emailTemplate = emailTemplateRepository.findById(templateId)
                    .orElseThrow(EmailTemplateNotFoundException::new);
            email.setTemplate(emailTemplate);

            if (updatingEmailOnlyIdDto.recipients() != null && !updatingEmailOnlyIdDto.recipients().isEmpty()) {
                List<Recipient> updatedRecipients = new ArrayList<>();
                for (OnlyIdRecipientDto recipientDto : updatingEmailOnlyIdDto.recipients()) {
                    UUID recipientId = recipientDto.id();
                    Recipient recipient = recipientRepository.findById(recipientId)
                            .orElseThrow(RecipientNotFoundException::new);
                    updatedRecipients.add(recipient);
                }
                email.setRecipients(updatedRecipients);
            }

            log.info("Email with id {} updated", id);
            return emailRepository.save(email);
        } else {
            log.error("Email not found with id {}", id);
            throw new EmailNotFoundException();
        }
    }

    public void delete(UUID id) {
        log.info("Deleting email template with id {}", id);
        if (getEmail(id).isPresent()) {
            log.info("Email template with id {} deleted", id);
            emailRepository.deleteById(id);
        } else {
            log.error("Email template not found with id {}", id);
            throw new EmailNotFoundException();
        }
    }

    public void send(UUID id) {
        List<EmailToBeSendDto> emailToBeSendDtos = prepareEmail(id);
        emailSenderService.sendEmail(id, emailToBeSendDtos);
    }

    private Optional<Email> getEmail(UUID id) {
        return emailRepository.findById(id);
    }

    public List<EmailToBeSendDto> prepareEmail(UUID emailId) {
        log.info("Preparing email with id {}", emailId);
        Email email = getById(emailId);
        return email.getRecipients().stream()
                .map(recipient -> new EmailToBeSendDto(
                        email.getId(),
                        recipient.getEmail(),
                        email.getTemplate().getSubject(),
                        email.getTemplate().getContent(),
                        List.of(new RecipientDto(
                                recipient.getId(),
                                recipient.getName(),
                                recipient.getEmail()
                        ))
                ))
                .toList();
    }
}
