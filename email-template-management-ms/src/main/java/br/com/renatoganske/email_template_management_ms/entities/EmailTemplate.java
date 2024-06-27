package br.com.renatoganske.email_template_management_ms.entities;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="email-templates")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String content;

    public EmailTemplateDto toDto() {
        return new EmailTemplateDto(id, name, content);
    }

}
