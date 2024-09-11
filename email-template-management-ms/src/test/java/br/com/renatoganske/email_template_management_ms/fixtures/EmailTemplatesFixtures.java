package br.com.renatoganske.email_template_management_ms.fixtures;

import br.com.renatoganske.email_template_management_ms.dtos.EmailTemplateDto;

import java.util.UUID;

public class EmailTemplatesFixtures {

    public static EmailTemplateDto emailTemplate() {
        return new EmailTemplateDto(
                UUID.randomUUID(),
                "Welcome Email",
                "Welcome Email",
                "<html><body>Template content [...]</body></html>"
        );
    }
}
