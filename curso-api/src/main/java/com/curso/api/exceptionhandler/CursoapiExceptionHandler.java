package com.curso.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
		String developermessage = ex.getCause().toString();
		return handleExceptionInternal(ex, new Error(usermessage, developermessage), headers, HttpStatus.BAD_REQUEST, request);
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
