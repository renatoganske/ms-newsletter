package br.com.renatoganske.email_template_management_ms.fixtures;

import br.com.renatoganske.email_template_management_ms.dtos.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class EmailFixtures {

    public static List<ToListEmailDto> createToListEmailDto() {
        List<ToListEmailDto> emailDtos = new ArrayList<>();
        UUID emailUuid = UUID.randomUUID();
        OnlyIdEmailTemplateDto templateDto = createOnlyIdEmailTemplateDto();
        List<OnlyIdRecipientDto> recipientDtos = createRecipientDtos();
        emailDtos.add(new ToListEmailDto(emailUuid, templateDto, recipientDtos));

        emailUuid = UUID.randomUUID();
        templateDto = createOnlyIdEmailTemplateDto();
        recipientDtos = createRecipientDtos();
        emailDtos.add(new ToListEmailDto(emailUuid, templateDto, recipientDtos));
        return emailDtos;
    }

    private static List<OnlyIdRecipientDto> createRecipientDtos() {
        return Arrays.asList(createOnlyIdRecipientDto(), createOnlyIdRecipientDto());
    }

    public static OnlyIdEmailTemplateDto createOnlyIdEmailTemplateDto() {
        return new OnlyIdEmailTemplateDto(UUID.randomUUID());
    }

    public static OnlyIdRecipientDto createOnlyIdRecipientDto() {
        return new OnlyIdRecipientDto(UUID.randomUUID());
    }

    public static EmailDto createEmail() {
        return new EmailDto(
                UUID.randomUUID(),
                EmailTemplatesFixtures.emailTemplate(),
                RecipientsFixtures.recipients()
        );
    }
}
