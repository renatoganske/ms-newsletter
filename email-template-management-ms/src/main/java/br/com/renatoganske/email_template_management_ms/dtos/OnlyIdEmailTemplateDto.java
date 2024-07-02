package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;


@Schema(description = "This DTO returns only the ID of an email template.")
public record OnlyIdEmailTemplateDto(

        UUID id

) implements Serializable {

        public EmailTemplate toEntity() {
                return EmailTemplate.builder()
                        .id(id)
                        .build();
        }

        public EmailTemplateDto toDto(EmailTemplate emailTemplate) {
                return new EmailTemplateDto(
                        emailTemplate.getId(),
                        emailTemplate.getName(),
                        emailTemplate.getSubject(),
                        emailTemplate.getContent()
                );
        }
}