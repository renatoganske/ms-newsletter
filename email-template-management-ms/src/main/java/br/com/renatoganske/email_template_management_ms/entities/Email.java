package br.com.renatoganske.email_template_management_ms.entities;

import br.com.renatoganske.email_template_management_ms.dtos.*;
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

    public EmailOnlyIdDto toOnlyIdDto() {
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

    public EmailDto toDto() {
        return new EmailDto(
                this.id,
                new EmailTemplateDto(
                        this.template.getId(),
                        this.template.getName(),
                        this.template.getSubject(),
                        this.template.getContent()
                ),
                this.recipients.stream()
                        .map(recipient -> new RecipientDto(
                                recipient.getId(),
                                recipient.getName(),
                                recipient.getEmail()
                        )).toList()
        );
    }


}
