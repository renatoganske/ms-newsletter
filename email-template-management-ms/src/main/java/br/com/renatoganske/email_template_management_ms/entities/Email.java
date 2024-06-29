package br.com.renatoganske.email_template_management_ms.entities;

import br.com.renatoganske.email_template_management_ms.dtos.EmailOnlyIdDto;
import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdEmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdRecipientDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="emails")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_template_id")
    private EmailTemplate template;

    @ManyToMany
    @JoinTable(name = "recipients_emails",
            joinColumns = @JoinColumn(name = "email_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    private List<Recipient> recipients = new ArrayList<>();

    public EmailOnlyIdDto toDto() {
        return new EmailOnlyIdDto(
                id,
                template.toOnlyIdDto(),
                recipients.stream().map(Recipient::toOnlyIdDto).toList());
    }

    public ToListEmailDto toListEmailDto() {
        OnlyIdEmailTemplateDto templateDto = new OnlyIdEmailTemplateDto(template.getId());
        List<OnlyIdRecipientDto> recipientDtos = new ArrayList<>();
        for (Recipient recipient : recipients) {
            recipientDtos.add(new OnlyIdRecipientDto(recipient.getId()));
        }

        return new ToListEmailDto(id, templateDto, recipientDtos);
    }
}
