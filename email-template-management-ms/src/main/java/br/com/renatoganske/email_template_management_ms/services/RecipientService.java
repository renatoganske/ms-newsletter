package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import br.com.renatoganske.email_template_management_ms.errorHandling.exception.business.RecipientNotFoundException;
import br.com.renatoganske.email_template_management_ms.repositories.RecipientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipientService {

    private final RecipientRepository recipientRepository;

    public List<RecipientDto> getAll() {
        log.info("Getting all recipients");
        return recipientRepository.findAll().stream().map(Recipient::toDto).toList();
    }

    public RecipientDto getById(UUID id) {
        log.info("Getting recipient with id {}", id);
        Optional<Recipient> recipient = getRecipient(id);
        if (recipient.isPresent()) {
            return recipient.get().toDto();
        }
        log.error("Recipient not found with id {}", id);
        throw new RecipientNotFoundException();
    }

    public Recipient save(RecipientDto recipientDto) {
        log.info("Saving recipient {}", recipientDto.id());
        return recipientRepository.save(recipientDto.toEntity());
    }

    public Recipient update(UUID id, RecipientDto updatingRecipientDto) {
        log.info("Updating email template with id {}", id);
        Optional<Recipient> emailTemplate = getRecipient(id);
        if (emailTemplate.isPresent()) {
            Recipient recipient = emailTemplate.get();
            recipient.setName(updatingRecipientDto.name());
            recipient.setEmail(updatingRecipientDto.email());
            log.info("Recipient with id {} updated", id);
            return recipientRepository.save(recipient);
        } else {
            log.error("Recipient not found with id {}", id);
            throw new RecipientNotFoundException();
        }
    }

    public void delete(UUID id) {
        log.info("Deleting recipient with id {}", id);
        if (getRecipient(id).isPresent()) {
            log.info("Recipient with id {} deleted", id);
            recipientRepository.deleteById(id);
        } else {
            log.error("Recipient not found with id {}", id);
            throw new RecipientNotFoundException();
        }
    }

    public Optional<Recipient> getRecipient(UUID id) {
        return recipientRepository.findById(id);
    }
}
