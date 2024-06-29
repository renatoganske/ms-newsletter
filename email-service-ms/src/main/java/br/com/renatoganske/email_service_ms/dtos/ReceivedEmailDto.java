package br.com.renatoganske.email_service_ms.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ReceivedEmailDto (
        @Schema(description = "Email template")
        @NotEmpty(message = "An email template must be provided.")
        EmailTemplateDto template,

        @Schema(description = "List of recipients")
        @NotEmpty(message = "At least one recipient must be provided.")
        List<RecipientDto> recipients
) {
}
