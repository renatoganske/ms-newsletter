package br.com.renatoganske.email_template_management_ms.producer;


import br.com.renatoganske.email_template_management_ms.dtos.*;
import br.com.renatoganske.email_template_management_ms.entities.Email;
import br.com.renatoganske.email_template_management_ms.repositories.EmailRepository;
import br.com.renatoganske.email_template_management_ms.services.EmailTemplateService;
import br.com.renatoganske.email_template_management_ms.services.RecipientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmailProducer {

    private final EmailTemplateService emailTemplateService;
    private final RecipientService recipientService;
    private final EmailRepository emailRepository;

    private ObjectMapper objectMapper;

    final RabbitTemplate rabbitTemplate;

    public EmailProducer(EmailTemplateService emailTemplateService,
                         RecipientService recipientService,
                         EmailRepository emailRepository,
                         RabbitTemplate rabbitTemplate,
                         ObjectMapper objectMapper) {
        this.emailTemplateService = emailTemplateService;
        this.recipientService = recipientService;
        this.emailRepository = emailRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(EmailOnlyIdDto emailOnlyIdDto) {
        // Buscar a entidade Email
        Email email = emailRepository.findById(emailOnlyIdDto.id())
                .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        // Converter para EmailDto
        EmailDto emailDto = email.toDto();

        // Para cada destinatário
        for (RecipientDto recipient : emailDto.recipients()) {
            try {
                // Criar uma nova mensagem com os detalhes do destinatário
                EmailDto individualEmailDto = new EmailDto(
                        emailDto.id(),
                        emailDto.template(),
                        List.of(recipient)
                );

                // Converter para JSON
                String emailJson = objectMapper.writeValueAsString(individualEmailDto);

                // Enviar para a fila do RabbitMQ
                rabbitTemplate.convertAndSend("your-exchange-name", "your-routing-key", emailJson);

            } catch (JsonProcessingException e) {
                // Tratar exceção de processamento de JSON
                e.printStackTrace();
            }
        }
    }
}