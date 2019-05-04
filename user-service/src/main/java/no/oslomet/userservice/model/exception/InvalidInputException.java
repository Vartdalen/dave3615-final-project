package no.oslomet.userservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason="Invalid input from client")
public class InvalidInputException extends RuntimeException {}
