package br.com.renatoganske.email_template_management_ms.services;

import br.com.renatoganske.email_template_management_ms.dtos.EmailOnlyIdDto;
import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdEmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.OnlyIdRecipientDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailDto;
import br.com.renatoganske.email_template_management_ms.entities.Email;
import br.com.renatoganske.email_template_management_ms.entities.EmailTemplate;
import br.com.renatoganske.email_template_management_ms.entities.Recipient;
import br.com.renatoganske.email_template_management_ms.fixtures.EmailFixtures;
import br.com.renatoganske.email_template_management_ms.repositories.EmailRepository;
import br.com.renatoganske.email_template_management_ms.repositories.EmailTemplateRepository;
import br.com.renatoganske.email_template_management_ms.repositories.RecipientRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    EmailService emailService;

    @Mock
    EmailRepository emailRepository;
    @Mock
    EmailTemplateRepository emailTemplateRepository;
    @Mock
    RecipientRepository recipientRepository;
    @Mock
    EmailSenderService emailSenderService;

    ToListEmailDto toListEmailDto;

    @Test
    void whenGetAllShouldReturnSuccess() {
        List<ToListEmailDto> emails = EmailFixtures.createToListEmailDto();
        when(emailRepository.findAll()).thenReturn(emails.stream()
                .map(ToListEmailDto::toEntity).toList());

        List<ToListEmailDto> result = emailService.getAll();
        MatcherAssert.assertThat(result.size(), is(2));
    }

    @Test
    void whenGetByIdShouldReturnSuccess() {

        Email email = EmailFixtures.createEmail().toEntity();
        when(emailRepository.findById(email.getId())).thenReturn(Optional.of(email));

        Email result = emailRepository.findById(email.getId()).orElseThrow(() -> new AssertionError("Email not found"));

        assertEquals(email.getId(), result.getId());
        assertEquals(email.getTemplate(), result.getTemplate());
        assertEquals(email.getRecipients(), result.getRecipients());
    }

    @Test
    void whenSaveShouldReturnSuccess() {

        Email email = EmailFixtures.createEmail().toEntity();

        when(emailRepository.save(any())).thenReturn(email);

        Email result = emailService.save(email.toOnlyIdDto());
        assertEquals(email.getId(), result.getId());
        assertEquals(email.getTemplate(), result.getTemplate());
        assertEquals(email.getRecipients(), result.getRecipients());
    }

    @Test
    void whenUpdateShouldReturnUpdatedEmail() {
        UUID emailId = UUID.randomUUID();
        EmailOnlyIdDto updatingDto = new EmailOnlyIdDto(
                emailId,
                new OnlyIdEmailTemplateDto(UUID.randomUUID()),
                List.of(new OnlyIdRecipientDto(UUID.randomUUID()))
        );

        Email existingEmail = EmailFixtures.createEmail().toEntity();
        existingEmail.setId(emailId);

        when(emailRepository.findById(emailId)).thenReturn(Optional.of(existingEmail));

        UUID updatedTemplateId = updatingDto.template().id();
        EmailTemplate updatedTemplate = new EmailTemplate(
                updatedTemplateId,
                "Updated Name",
                "Updated Subject",
                "Updated Content");
        when(emailTemplateRepository.findById(updatedTemplateId)).thenReturn(Optional.of(updatedTemplate));

        UUID recipientId = updatingDto.recipients().get(0).id();
        Recipient updatedRecipient = new Recipient(recipientId, "Updated Recipient", "updated_recipient@example.com", null);
        when(recipientRepository.findById(recipientId)).thenReturn(Optional.of(updatedRecipient));

        Email updatedEmail = Email.builder()
                .id(emailId)
                .template(updatedTemplate)
                .recipients(List.of(updatedRecipient))
                .build();

        when(emailRepository.save(any(Email.class))).thenReturn(updatedEmail);

        Email result = emailService.update(emailId, updatingDto);

        assertEquals(emailId, result.getId());
        assertEquals("Updated Subject", result.getTemplate().getSubject());
        assertEquals("Updated Content", result.getTemplate().getContent());
        assertEquals(1, result.getRecipients().size());
        assertEquals("Updated Recipient", result.getRecipients().get(0).getName());
        assertEquals("updated_recipient@example.com", result.getRecipients().get(0).getEmail());

        verify(emailRepository).findById(emailId);
        verify(emailTemplateRepository).findById(updatedTemplateId);
        verify(recipientRepository).findById(recipientId);
        verify(emailRepository).save(existingEmail);
    }





}