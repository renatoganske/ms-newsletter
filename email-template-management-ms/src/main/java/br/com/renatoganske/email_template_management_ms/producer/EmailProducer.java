package br.com.renatoganske.email_template_management_ms.producer;


import br.com.renatoganske.email_template_management_ms.dtos.EmailToBeSendDto;
import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import br.com.renatoganske.email_template_management_ms.entities.Email;
import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import br.com.renatoganske.email_template_management_ms.repositories.EmailRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class EmailProducer {

    private final EmailRepository emailRepository;

    private final ObjectMapper objectMapper;

    final RabbitTemplate rabbitTemplate;

    public EmailProducer(EmailRepository emailRepository,
                         RabbitTemplate rabbitTemplate,
                         ObjectMapper objectMapper) {
        this.emailRepository = emailRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(UUID id) {
        Email email = emailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        for (Recipient recipient : email.getRecipients()) {
            EmailToBeSendDto emailToBeSendDto = new EmailToBeSendDto(
                    email.getId(),
                    recipient.getEmail(),
                    email.getTemplate().getSubject(),
                    email.getTemplate().getContent(),
                    List.of(
                            new RecipientDto(
                                recipient.getId(),
                                recipient.getName(),
                                recipient.getEmail()
                            ))
            );

            try {
                String emailJson = objectMapper.writeValueAsString(emailToBeSendDto);
                rabbitTemplate.convertAndSend("", routingKey, emailJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}