package br.com.renatoganske.email_template_management_ms.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

@Schema(description = "This DTO represents an email.")
public record EmailDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email ID")
        UUID id,

        @Schema(description = "Email template")
        EmailTemplateDto template,

        @Schema(description = "List of recipients")
        @NotEmpty(message = "At least one recipient must be provided.")
        List<RecipientDto> recipients
) {
}
