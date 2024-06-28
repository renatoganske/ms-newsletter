package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "This DTO represents a recipient.")
public record RecipientDto(

        UUID id,
        @NotEmpty(message = "Please provide a valid name.")
        String name,
        @Email(message = "Please provide a valid email.")
        @NotEmpty(message = "Please provide an email.")
        String email

) implements Serializable {

        public Recipient toEntity() {
                return Recipient.builder()
                        .id(id)
                        .name(name)
                        .email(email)
                        .build();
        }
}