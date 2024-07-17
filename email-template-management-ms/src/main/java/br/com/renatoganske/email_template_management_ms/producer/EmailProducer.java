package br.com.renatoganske.email_template_management_ms.producer;


import br.com.renatoganske.email_template_management_ms.dtos.EmailToBeSendDto;
import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import br.com.renatoganske.email_template_management_ms.entities.Email;
import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import br.com.renatoganske.email_template_management_ms.repositories.EmailRepository;
import br.com.renatoganske.email_template_management_ms.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

//TODO INCLUIR LOGS
//TODO JAVA DOC
@Component
public class EmailProducer {

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    final RabbitTemplate rabbitTemplate;

    public EmailProducer(EmailService emailService,
                         RabbitTemplate rabbitTemplate,
                         ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    //TODO VERIFICAR AS CONFIGURAÇÕES PARA RETRY E DLQ
    public void publishMessageEmail(UUID id) {

        List<EmailToBeSendDto> emailToBeSendDtos = emailService.prepareEmail(id);
        emailToBeSendDtos.forEach(this::sendEmail);
    }

    private void sendEmail(EmailToBeSendDto emailToBeSendDto) {
        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(emailToBeSendDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
