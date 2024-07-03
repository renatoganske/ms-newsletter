package br.com.renatoganske.email_template_management_ms.dtos;

import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.UUID;

@Schema(description = "This DTO returns only the ID of a recipient.")
public record OnlyIdRecipientDto(

        UUID id

) implements Serializable {

        public Recipient toEntity() {
                return Recipient.builder()
                        .id(id)
                        .build();
        }
}