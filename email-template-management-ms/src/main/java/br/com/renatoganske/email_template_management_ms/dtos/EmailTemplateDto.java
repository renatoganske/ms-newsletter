package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;


@Schema(description = "Esse DTO representa um template de email.")
public record EmailTemplateDto(

        UUID id,
        @Size(message = "O nome deve ter no mínimo 3 e no máximo 50 caracteres.", min = 3, max = 50)
        @NotEmpty(message = "Informe um nome para o template.")
        String name,
        @NotEmpty(message = "Informe o template.")
        String content) implements Serializable {

        public EmailTemplate toEntity() {
            return EmailTemplate.builder()
                    .id(id)
                    .name(name)
                    .content(content)
                    .build();
        }
}