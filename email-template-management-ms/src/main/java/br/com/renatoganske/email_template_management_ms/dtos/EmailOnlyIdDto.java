package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Email;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

@Schema(description = "This DTO represents an email.")
public record EmailOnlyIdDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email ID")
        UUID id,

        @Schema(description = "Email template")
        @NotEmpty(message = "An email template must be provided.")
        OnlyIdEmailTemplateDto template,

        @Schema(description = "List of recipients")
        @NotEmpty(message = "At least one recipient must be provided.")
        List<OnlyIdRecipientDto> recipients
) {

    public Email toEntity() {
        return Email.builder()
                .id(id)
                .template(template.toEntity())
                .recipients(recipients.stream().map(OnlyIdRecipientDto::toEntity).toList())
                .build();

    }
}
