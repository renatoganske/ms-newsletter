package br.com.renatoganske.email_template_management_ms.controller.impl;

import br.com.renatoganske.email_template_management_ms.controller.IRecipientController;
import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import br.com.renatoganske.email_template_management_ms.services.EmailTemplateService;
import br.com.renatoganske.email_template_management_ms.services.RecipientService;
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
public class RecipientController implements IRecipientController {

    private final RecipientService service;

    @Override
    public ResponseEntity<List<RecipientDto>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    public ResponseEntity<RecipientDto> findById(UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<RecipientDto> save(RecipientDto recipientDto) {
        RecipientDto savedRecipientDto = service.save(recipientDto).toDto();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRecipientDto.id())
                .toUri();

        return ResponseEntity.created(location).body(savedRecipientDto);
    }

    @Override
    public ResponseEntity<RecipientDto> update(@PathVariable UUID id,
                                               @Valid @RequestBody RecipientDto recipientDto) {
        return ResponseEntity.ok(service.update(id, recipientDto).toDto());
    }

    @Override
    public void delete(UUID id) {
        service.delete(id);
    }
}
