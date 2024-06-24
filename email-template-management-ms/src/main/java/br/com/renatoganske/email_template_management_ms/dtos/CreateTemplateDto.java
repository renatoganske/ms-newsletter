package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Template;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Template}
 */
public record CreateTemplateDto(

        UUID id,
        @Size(message = "O nome deve ter no mínimo 3 e no máximo 50 caracteres.", min = 3, max = 50)
        @NotEmpty(message = "Informe um nome para o template.")
        String name,
        @NotEmpty(message = "Informe o template.")
        String content) implements Serializable {
}