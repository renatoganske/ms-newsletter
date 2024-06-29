package br.com.renatoganske.email_service_ms.dtos;

import br.com.renatoganske.email_service_ms.entities.Email;
import br.com.renatoganske.email_service_ms.enums.StatusEmail;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmailDto(



        @Schema(description = "Email ID")
        UUID emailId,

        @Schema(description = "Email template ID")
        UUID emailTemplateId,

        @Schema(description = "Email sender")
        String emailFrom,

        @Schema(description = "Email recipient")
        String emailTo,

        @Schema(description = "Email name")
        String emailName,

        @Schema(description = "Email subject")
        String subject,

        @Schema(description = "Email content")
        String content,

        @Schema(description = "Email send date")
        LocalDateTime sendDateEmail,

        @Schema(description = "Email status")
        StatusEmail statusEmail,

        @Schema(description = "Recipient ID")
        UUID recipientId,

        @Schema(description = "Recipient name")
        String recipientName) {

        public Email toEntity() {
            return Email.builder()
                    .emailId(emailId)
                    .emailTemplateId(emailTemplateId)
                    .emailFrom(emailFrom)
                    .emailTo(emailTo)
                    .emailName(emailName)
                    .subject(subject)
                    .content(content)
                    .sendDateEmail(sendDateEmail)
                    .statusEmail(statusEmail)
                    .recipientId(recipientId)
                    .recipientName(recipientName)
                    .build();
        }
}

