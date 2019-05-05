package no.oslomet.gatewayservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="Invalid password")
public class InvalidCredentialException extends RuntimeException {}
