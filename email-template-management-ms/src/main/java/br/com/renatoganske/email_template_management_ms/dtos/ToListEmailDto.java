package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Email;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "This DTO represents a resume of an email.")
public record ToListEmailDto(

        UUID id,
        OnlyIdEmailTemplateDto template,
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
