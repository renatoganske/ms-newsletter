package br.com.renatoganske.email_template_management_ms.errorHandling.exception.contract;

import java.util.Map;

public interface MessageException {
    String getExceptionKey();

    Map<String, Object> getMapDetails();
}
