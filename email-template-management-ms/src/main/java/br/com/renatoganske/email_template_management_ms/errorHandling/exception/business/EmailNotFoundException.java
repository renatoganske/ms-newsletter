package br.com.renatoganske.email_template_management_ms.errorHandling.exception.business;

import br.com.renatoganske.email_template_management_ms.errorHandling.BaseRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailNotFoundException extends BaseRuntimeException {
    private static final String KEY = "findById.email.rule";

    public EmailNotFoundException() {
        super();
    }

    @Override
    public String getExceptionKey() {
        return KEY;
    }
}
