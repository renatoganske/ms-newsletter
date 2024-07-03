package br.com.renatoganske.email_service_ms.consumers;

import br.com.renatoganske.email_service_ms.dtos.EmailDto;
import br.com.renatoganske.email_service_ms.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload String receivedEmail) throws JsonProcessingException {

        EmailDto emailDto = new ObjectMapper().readValue(receivedEmail, EmailDto.class);
        emailService.send(emailDto);
    }
}
