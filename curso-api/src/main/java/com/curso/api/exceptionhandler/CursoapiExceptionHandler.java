package com.curso.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CursoapiExceptionHandler extends ResponseEntityExceptionHandler{

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String usermessage = messageSource.getMessage("MSG001", null, LocaleContextHolder.getLocale());
		String developermessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
	
		List<Error> erros = Arrays.asList(new Error(usermessage, developermessage));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		List<Error> errors = listOfErrors(ex.getBindingResult());
		return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

		String usermessage = messageSource.getMessage("MSG002", null, LocaleContextHolder.getLocale());
		String developermessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(usermessage, developermessage));

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	private List<Error> listOfErrors(BindingResult bindingResult){
		List<Error> errors = new ArrayList<>();
	
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String usermessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String developermessage = fieldError.toString();
			errors.add(new Error(usermessage, developermessage));
			
		}
		return errors;
	}

	public static class Error{

		private String usermessage;
		private String developermessage;

		public Error(String usermessage, String developermessage) {

			this.usermessage = usermessage;
			this.developermessage = developermessage;
		}

		public String getUsermessage() {
			return usermessage;
		}

		public String getDevelopermessage() {
			return developermessage;
		}
		
	}
}
