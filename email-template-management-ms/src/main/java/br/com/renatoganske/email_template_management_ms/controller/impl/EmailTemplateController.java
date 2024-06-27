package br.com.renatoganske.email_template_management_ms.controller.impl;

import br.com.renatoganske.email_template_management_ms.controller.IEmailTemplateController;
import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.services.EmailTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailTemplateController implements IEmailTemplateController {

    private final EmailTemplateService service;

    @Override
    public ResponseEntity<List<ToListEmailTemplateDto>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<EmailTemplateDto> findById(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<EmailTemplateDto> save(EmailTemplateDto emailTemplateDto) {
        EmailTemplateDto savedEmailTemplateDto = service.save(emailTemplateDto).toDto();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmailTemplateDto.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEmailTemplateDto);
    }

    @Override
    public ResponseEntity<EmailTemplateDto> update(@PathVariable UUID id,
                                                           @Valid @RequestBody EmailTemplateDto emailTemplateDto) {
        return ResponseEntity.ok(service.update(id, emailTemplateDto).toDto());
    }

    @Override
    public void delete(UUID id) {
        service.delete(id);
    }
}
