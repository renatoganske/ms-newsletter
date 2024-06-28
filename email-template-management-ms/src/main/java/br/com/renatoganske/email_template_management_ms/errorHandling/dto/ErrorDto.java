package br.com.renatoganske.email_template_management_ms.errorHandling.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorDto {

    private String key;
    private String message;

}
