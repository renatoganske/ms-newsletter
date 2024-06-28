package br.com.renatoganske.email_template_management_ms.controller;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailTemplateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/email-templates")
@Tag(name = "Email Template Management")
public interface IEmailTemplateController {

    @Operation(summary = "List all email templates", description = "Finds all email templates.")
    @GetMapping
    ResponseEntity<List<ToListEmailTemplateDto>> findAll();

    @Operation(summary = "Find email template by id", description = "Find a email template by id.")
    @GetMapping("/{id}")
    ResponseEntity<EmailTemplateDto> findById(@PathVariable UUID id);

    @Operation(summary = "Save new email template", description = "Saves a new email template.")
    @PostMapping
    ResponseEntity<EmailTemplateDto> save(@Valid @RequestBody EmailTemplateDto emailTemplateDto);

    @Operation(summary = "Update email template", description = "Updates an existing email template.")
    @PutMapping("/{id}")
    ResponseEntity<EmailTemplateDto> update(@PathVariable UUID id, @Valid @RequestBody EmailTemplateDto emailTemplateDto);

    @Operation(summary = "Excludes email template", description = "Excludes an existing email template.")
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);
}
