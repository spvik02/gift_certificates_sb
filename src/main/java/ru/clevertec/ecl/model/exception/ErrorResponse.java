package ru.clevertec.ecl.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String errorMessage;
    private String path;
}
