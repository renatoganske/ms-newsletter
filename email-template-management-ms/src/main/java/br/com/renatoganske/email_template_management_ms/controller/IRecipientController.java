package br.com.renatoganske.email_template_management_ms.controller;

import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipients")
@Tag(name = "Recipient Controller")
public interface IRecipientController {


    @Operation(summary = "List all recipients", description = "Finds all recipients.")
    @GetMapping
    ResponseEntity<List<RecipientDto>> findAll();

    @Operation(summary = "Find recipient by id", description = "Find a recipient by id.")
    @GetMapping("/{id}")
    ResponseEntity<RecipientDto> findById(@PathVariable UUID id);

    @Operation(summary = "Save new recipient", description = "Saves a new recipient.")
    @PostMapping
    ResponseEntity<RecipientDto> save(@Valid @RequestBody RecipientDto recipientDto);

    @Operation(summary = "Update recipient", description = "Updates an existing recipient.")
    @PutMapping("/{id}")
    ResponseEntity<RecipientDto> update(@PathVariable UUID id, @Valid @RequestBody RecipientDto recipientDto);

    @Operation(summary = "Excludes recipient", description = "Excludes an existing recipient.")
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

}
