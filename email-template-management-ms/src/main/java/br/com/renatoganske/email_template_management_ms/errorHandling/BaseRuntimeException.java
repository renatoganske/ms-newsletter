package br.com.renatoganske.email_template_management_ms.errorHandling;

import br.com.renatoganske.email_template_management_ms.errorHandling.exception.contract.MessageException;

import java.util.Map;

public abstract class BaseRuntimeException extends RuntimeException implements MessageException {
    private final Map<String, Object> mapDetails;

    public BaseRuntimeException() {
        mapDetails = null;
    }

    public BaseRuntimeException(final Map<String, Object> mapDetails) {
        this.mapDetails = mapDetails;
    }

    public abstract String getExceptionKey();

    public Map<String, Object> getMapDetails() {
        return this.mapDetails;
    }
}
