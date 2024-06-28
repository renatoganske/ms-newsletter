package br.com.renatoganske.email_template_management_ms.entities;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "template_id")
    private EmailTemplate template;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "email_recipient",
            joinColumns = @JoinColumn(name = "email_id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id")
    )
    private List<Recipient> recipients;
}
