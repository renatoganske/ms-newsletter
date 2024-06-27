package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;


@Schema(description = "Esse DTO representa um template de email.")
public record ToListEmailTemplateDto(

        @Schema(example = "123e4567-e89b-12d3-a456-426614174000", description = "Email Template ID")
        UUID id,
        @Schema(example = "Welcome Email", description = "Template name")
        String name
) implements Serializable {
}