package br.com.renatoganske.email_template_management_ms.controller;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/email-templates")
public interface IEmailTemplateController {

    @Operation(summary = "Listar todos os templates de emails", description = "Busca todos os templates de emails.")
    @GetMapping
    ResponseEntity<List<EmailTemplateDto>> findAll();

    @Operation(summary = "Buscar template de email", description = "Busca um determinado template de email pelo id.")
    @GetMapping("/{id}")
    ResponseEntity<EmailTemplateDto> findById(@PathVariable UUID id);

    @Operation(summary = "Salvar template de email", description = "Salva um novo template de email.")
    @PostMapping
    ResponseEntity<EmailTemplateDto> save(@Valid @RequestBody EmailTemplateDto emailTemplateDto);

    @Operation(summary = "Editar template de email", description = "Edita um determinado template de email.")
    @PutMapping("/{id}")
    ResponseEntity<EmailTemplateDto> update(@PathVariable UUID id, @Valid @RequestBody EmailTemplateDto emailTemplateDto);

    @Operation(summary = "Excluir template de email", description = "Exclui um determinado template de email.")
    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

}
