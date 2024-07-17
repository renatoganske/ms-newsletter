package br.com.renatoganske.email_template_management_ms.fixtures;

import br.com.renatoganske.email_template_management_ms.dtos.RecipientDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RecipientsFixtures {

    public static RecipientDto recipient() {
        return new RecipientDto(
                UUID.randomUUID(),
                "Name",
                "email@email.com"
        );
    }

    public static List<RecipientDto> recipients() {
        return Arrays.asList(
                recipient(),
                recipient()
        );
    }
}
