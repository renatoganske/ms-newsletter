package br.com.renatoganske.email_service_ms.entities;

import br.com.renatoganske.email_service_ms.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private UUID emailId;
    private UUID emailTemplateId;
    private String emailFrom;
    private String emailTo;
    private String emailName;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime sendDateEmail;
    private StatusEmail statusEmail;
    private UUID recipientId;
    private String recipientName;

}
