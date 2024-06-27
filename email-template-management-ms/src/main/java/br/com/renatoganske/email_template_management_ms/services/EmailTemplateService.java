package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import br.com.renatoganske.email_template_management_ms.repositories.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailTemplateService {

    private final EmailTemplateRepository emailTemplateRepository;

    public List<ToListEmailTemplateDto> getAll() {
        log.info("Getting all email templates");
        return emailTemplateRepository.findAll().stream().map(EmailTemplate::toListDto).toList();
    }

    public EmailTemplateDto getById(UUID id) {
        log.info("Getting email template with id {}", id);
        Optional<EmailTemplate> emailTemplate = getEmailTemplate(id);
        if (emailTemplate.isPresent()) {
            return emailTemplate.get().toDto();
        }
        log.error("Email template not found with id {}", id);
        throw new RuntimeException("Email template not found with id " + id);
    }

    public EmailTemplate save(EmailTemplateDto emailTemplateDto) {
        log.info("Saving email template {}", emailTemplateDto.name());
        return emailTemplateRepository.save(emailTemplateDto.toEntity());
    }

    public EmailTemplate update(UUID id, EmailTemplateDto updatingEmailTemplateDto) {
        log.info("Updating email template with id {}", id);
        Optional<EmailTemplate> emailTemplate = getEmailTemplate(id);
        if (emailTemplate.isPresent()) {
            EmailTemplate template = emailTemplate.get();
            template.setName(updatingEmailTemplateDto.name());
            template.setContent(updatingEmailTemplateDto.content());
            log.info("Email template with id {} updated", id);
            return emailTemplateRepository.save(template);
        } else {
            log.error("Email template not found with id {}", id);
            throw new RuntimeException("Template not found with id " + id);
        }
    }

    public void delete(UUID id) {
        log.info("Deleting email template with id {}", id);
        if (getEmailTemplate(id).isPresent()) {
            log.info("Email template with id {} deleted", id);
            emailTemplateRepository.deleteById(id);
        } else {
            log.error("Email template not found with id {}", id);
            throw new RuntimeException("Template not found with id " + id);
        }
    }

    private Optional<EmailTemplate> getEmailTemplate(UUID id) {
        return emailTemplateRepository.findById(id);
    }


}
