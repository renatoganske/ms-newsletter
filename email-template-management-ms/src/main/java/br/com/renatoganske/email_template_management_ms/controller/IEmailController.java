package br.com.renatoganske.email_template_management_ms.controller;

import br.com.renatoganske.email_template_management_ms.dtos.EmailDto;
import br.com.renatoganske.email_template_management_ms.dtos.ToListEmailDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/email")
@Tag(name = "Recipient Controller")
public interface IEmailController {


    @Operation(summary = "List all emails", description = "Finds all emails.")
    @GetMapping
    ResponseEntity<List<ToListEmailDto>> findAll();

    @Operation(summary = "Find email by id", description = "Find a email by id.")
    @GetMapping("/{id}")
    ResponseEntity<EmailDto> findById(@PathVariable UUID id);

    @Operation(summary = "Save new email", description = "Saves a new email.")
    @PostMapping
    ResponseEntity<EmailDto> save(@Valid @RequestBody EmailDto emailDto);

    @Operation(summary = "Update email", description = "Updates an existing email.")
    @PutMapping("/{id}")
    ResponseEntity<EmailDto> update(@PathVariable UUID id, @Valid @RequestBody EmailDto emailDto);

    @Operation(summary = "Excludes email", description = "Excludes an existing email.")
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

    @Operation(summary = "Send email", description = "Sends an email.")
    @PostMapping("/{id}")
    void send(@PathVariable UUID id);

}
