package no.oslomet.userservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason="User already exists")
public class UserExistsException extends RuntimeException {}
