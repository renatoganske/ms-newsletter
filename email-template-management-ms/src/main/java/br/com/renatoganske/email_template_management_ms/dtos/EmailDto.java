package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

@Schema(description = "This DTO represents an email.")
public record EmailDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email ID")
        UUID id,

        @Schema(description = "Email template")
        @NotEmpty(message = "An email template must be provided.")
        EmailTemplateDto template,

        @Schema(description = "List of recipients")
        @NotEmpty(message = "At least one recipient must be provided.")
        List<RecipientDto> recipients
) {

    public Email toEntity() {
        return Email.builder()
                .id(id)
                .template(template.toEntity())
                .recipients(recipients.stream().map(RecipientDto::toEntity).toList())
                .build();

    }

    public static EmailDto fromEntity(Email email) {
        return new EmailDto(
                email.getId(),
                new EmailTemplateDto(
                        email.getTemplate().getId(),
                        email.getTemplate().getName(),
                        email.getTemplate().getSubject(),
                        email.getTemplate().getContent()
                ),
                email.getRecipients().stream()
                        .map(recipient -> new RecipientDto(
                                recipient.getId(),
                                recipient.getName(),
                                recipient.getEmail()
                        ))
                        .toList()
        );
    }

}
