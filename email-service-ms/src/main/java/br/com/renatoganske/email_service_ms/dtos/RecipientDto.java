package br.com.renatoganske.email_service_ms.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record RecipientDto(
        @NotEmpty(message = "The recipient ID must be provided.")
        UUID id,

        @NotEmpty(message = "The recipient email must be provided.")
        String email,

        @NotEmpty(message = "The recipient name must be provided.")
        String name
) {
}