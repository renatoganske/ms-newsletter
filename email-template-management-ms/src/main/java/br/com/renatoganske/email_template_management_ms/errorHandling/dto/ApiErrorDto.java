package br.com.renatoganske.email_template_management_ms.errorHandling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ApiErrorDto {
    private Date timestamp;
    private Integer status;
    private String code;
    private Set<ErrorDto> errors;
}
