package br.com.renatoganske.email_template_management_ms.producer;


import br.com.renatoganske.email_template_management_ms.dtos.EmailToBeSendDto;
import br.com.renatoganske.email_template_management_ms.services.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

//TODO INCLUIR LOGS
//TODO JAVA DOC
@Slf4j
@Component
public class EmailProducer {

    private final ObjectMapper objectMapper;

    final RabbitTemplate rabbitTemplate;

    public EmailProducer(RabbitTemplate rabbitTemplate,
                         ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    //TODO VERIFICAR AS CONFIGURAÇÕES PARA RETRY E DLQ
    public void sendEmail(EmailToBeSendDto emailToBeSendDto) {
        try {
            log.info("Sending email to {}", emailToBeSendDto.getEmailTo());
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(emailToBeSendDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
