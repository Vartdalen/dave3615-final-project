package no.oslomet.followservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason="Follow self not allowed")
public class FollowSelfException extends RuntimeException {}
