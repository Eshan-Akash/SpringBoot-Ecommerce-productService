package dev.eshan.productservice.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionDto {
    private HttpStatus errorCode;
    private String message;

    public ExceptionDto(HttpStatus errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
