package no.oslomet.followservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason="Follow already exists")
public class FollowExistsException extends RuntimeException {}
