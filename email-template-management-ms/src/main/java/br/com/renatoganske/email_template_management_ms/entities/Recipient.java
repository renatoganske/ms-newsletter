package br.com.renatoganske.email_template_management_ms.entities;

import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdRecipientDto;
import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipients-email-templates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String email;

    @ManyToMany(mappedBy = "recipients", cascade = CascadeType.ALL)
    private List<Email> emails;

    public RecipientDto toDto() {
        return new RecipientDto(id, name, email);
    }

    public OnlyIdRecipientDto toOnlyIdDto() {
        return new OnlyIdRecipientDto(id);
    }
}
