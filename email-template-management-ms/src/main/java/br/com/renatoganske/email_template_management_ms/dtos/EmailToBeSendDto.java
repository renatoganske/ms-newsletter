package br.com.renatoganske.email_template_management_ms.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailToBeSendDto implements Serializable {

    @Schema(description = "Email ID")
    private UUID emailId;

    @Schema(description = "Email recipient")
    private String emailTo;

    @Schema(description = "Email subject")
    private String subject;

    @Schema(description = "Email content")
    private String content;

    @Schema(description = "List of recipients")
    private List<RecipientDto> recipients;
}