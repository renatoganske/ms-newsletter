package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.EmailToBeSendDto;
import br.com.renatoganske.email_template_management_ms.producer.EmailProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    private final EmailProducer emailProducer;

    public void sendEmail(List<EmailToBeSendDto> emailToBeSendDtos) {
        emailToBeSendDtos.forEach(emailProducer::sendEmail);
    }
}
