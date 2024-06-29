package br.com.renatoganske.email_service_ms.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;


@Schema(description = "This DTO represents an email template.")
public record EmailTemplateDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email Template ID")
        UUID id,
        @Schema(example = "Welcome Email", description = "Template name")
        @NotEmpty(message = "A name must be informed.")
        String name,
        @Schema(example = "Welcome Email", description = "Email template subject")
        @Size(message = "The subject must be between 3 and 50 characters.", min = 3, max = 50)
        @NotEmpty(message = "A subject must be informed.")
        String subject,
        @Schema(example = "<html><body>Template content [...]</body></html>", description = "Template content")
        @NotEmpty(message = "A content must be informed.")
        String content

) implements Serializable {
}