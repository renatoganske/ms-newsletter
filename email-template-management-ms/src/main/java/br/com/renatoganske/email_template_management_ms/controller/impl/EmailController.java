package br.com.renatoganske.email_template_management_ms.controller.impl;

import br.com.renatoganske.email_template_management_ms.controller.IEmailController;
import br.com.renatoganske.email_template_management_ms.dtos.EmailOnlyIdDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailDto;
import br.com.renatoganske.email_template_management_ms.services.EmailService;
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
public class EmailController implements IEmailController {

    private final EmailService service;

    @Override
    public ResponseEntity<List<ToListEmailDto>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<EmailOnlyIdDto> findById(UUID id) {
        return ResponseEntity.ok(service.getById(id).toDto());
    }

    @Override
    public ResponseEntity<EmailOnlyIdDto> save(EmailOnlyIdDto emailOnlyIdDto) {
        EmailOnlyIdDto savedEmailOnlyIdDto = service.save(emailOnlyIdDto).toDto();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmailOnlyIdDto.id())
                .toUri();

        return ResponseEntity.created(location).body(savedEmailOnlyIdDto);
    }

    @Override
    public ResponseEntity<EmailOnlyIdDto> update(@PathVariable UUID id,
                                                 @Valid @RequestBody EmailOnlyIdDto emailOnlyIdDto) {
        return ResponseEntity.ok(service.update(id, emailOnlyIdDto).toDto());
    }

    @Override
    public void delete(UUID id) {
        service.delete(id);
    }

    @Override
    public void send(UUID id) {

    }
}
