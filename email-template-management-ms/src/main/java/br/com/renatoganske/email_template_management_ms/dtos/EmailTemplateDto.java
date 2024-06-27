package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;


@Schema(description = "Esse DTO representa um template de email.")
public record EmailTemplateDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email Template ID")
        UUID id,
        @Schema(example = "Welcome Email", description = "Template name")
        @Size(message = "The name must be between 3 and 50 characters.", min = 3, max = 50)
        @NotEmpty(message = "A name must be informed.")
        String name,
        @Schema(example = "<html><body>Template content [...]</body></html>", description = "Template content")
        @NotEmpty(message = "A content must be informed.")
        String content) implements Serializable {

        public EmailTemplate toEntity() {
            return EmailTemplate.builder()
                    .id(id)
                    .name(name)
                    .content(content)
                    .build();
        }
}