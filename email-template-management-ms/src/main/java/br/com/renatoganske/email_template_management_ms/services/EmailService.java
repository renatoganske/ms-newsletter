package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.EmailDto;
import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdRecipientDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailDto;
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

    private final EmailRepository repository;
    private final EmailTemplateRepository emailTemplateRepository;
    private final RecipientRepository recipientRepository;

    public List<ToListEmailDto> getAll() {
        log.info("Getting all emails");
        return repository.findAll().stream().map(Email::toListEmailDto).toList();
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


    public Email save(EmailDto emailDto) {
        log.info("Saving email {}", emailDto.id());
        return repository.save(emailDto.toEntity());
    }


    public Email update(UUID id, EmailDto updatingEmailDto) {
        log.info("Updating email with id {}", id);

        Optional<Email> optionalEmail = getEmail(id);
        if (optionalEmail.isPresent()) {

            Email email = optionalEmail.get();
            UUID templateId = updatingEmailDto.template().id();
            EmailTemplate emailTemplate = emailTemplateRepository.findById(templateId)
                    .orElseThrow(() -> new EmailTemplateNotFoundException());
            email.setTemplate(emailTemplate);

            if (updatingEmailDto.recipients() != null && !updatingEmailDto.recipients().isEmpty()) {
                List<Recipient> updatedRecipients = new ArrayList<>();
                for (OnlyIdRecipientDto recipientDto : updatingEmailDto.recipients()) {
                    UUID recipientId = recipientDto.id();
                    Recipient recipient = recipientRepository.findById(recipientId)
                            .orElseThrow(() -> new RecipientNotFoundException());
                    updatedRecipients.add(recipient);
                }
                email.setRecipients(updatedRecipients);
            }

            log.info("Email with id {} updated", id);
            return repository.save(email);
        } else {
            log.error("Email not found with id {}", id);
            throw new EmailNotFoundException();
        }
    }

    public void delete(UUID id) {
        log.info("Deleting email template with id {}", id);
        if (getEmail(id).isPresent()) {
            log.info("Email template with id {} deleted", id);
            repository.deleteById(id);
        } else {
            log.error("Email template not found with id {}", id);
            throw new EmailNotFoundException();
        }
    }

    public void send(EmailDto emailDto) {
    //TODO implementar o producer do rabbitMQ
    }

    private Optional<Email> getEmail(UUID id) {
        return repository.findById(id);
    }

}
