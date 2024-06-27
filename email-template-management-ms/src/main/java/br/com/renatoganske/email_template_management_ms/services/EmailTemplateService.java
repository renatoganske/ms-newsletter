package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
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

    public List<EmailTemplateDto> getAll() {
        return emailTemplateRepository.findAll().stream().map(EmailTemplate::toDto).toList();
    }

    public EmailTemplateDto getById(UUID id) {
        Optional<EmailTemplate> emailTemplate = getEmailTemplate(id);
        if (emailTemplate.isPresent()) {
            return emailTemplate.get().toDto();
        }

            throw new RuntimeException("Email template not found with id " + id);
    }

    public EmailTemplate save(EmailTemplateDto emailTemplateDto) {
        return emailTemplateRepository.save(emailTemplateDto.toEntity());
    }

    public EmailTemplate update(UUID id, EmailTemplateDto updatingEmailTemplateDto) {
        Optional<EmailTemplate> emailTemplate = getEmailTemplate(id);
        if (emailTemplate.isPresent()) {
            EmailTemplate template = emailTemplate.get();
            template.setName(updatingEmailTemplateDto.name());
            template.setContent(updatingEmailTemplateDto.content());
            return emailTemplateRepository.save(template);
        } else {
            throw new RuntimeException("Template not found with id " + id);
        }
    }

    public void delete(UUID id) {
        if (emailTemplateRepository.existsById(id)) {
            emailTemplateRepository.deleteById(id);
        } else {
            throw new RuntimeException("Template not found with id " + id);
        }
    }

    private Optional<EmailTemplate> getEmailTemplate(UUID id) {
        return emailTemplateRepository.findById(id);
    }


}
