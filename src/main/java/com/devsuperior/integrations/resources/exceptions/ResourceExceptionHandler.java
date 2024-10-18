package com.devsuperior.integrations.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.integrations.dto.StandardErrorDTO;
import com.devsuperior.integrations.services.exceptions.EmailException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<StandardErrorDTO> email(EmailException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardErrorDTO err = new StandardErrorDTO();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Email error");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}	
}
