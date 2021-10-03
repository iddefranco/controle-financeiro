package br.com.controlefinanceiro.controlefinanceiro.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ServiceException extends RuntimeException {
	protected String codigo;

	protected String message;

	protected HttpStatus httpStatus;

	public ServiceException() {
	}

	public ServiceException(String codigo, String message) {
		this.codigo = codigo;
		this.message = message;
	}

	public ServiceException(String codigo, String message, HttpStatus httpStatus) {
		this.codigo = codigo;
		this.message = message;
		this.httpStatus = httpStatus;
	}

}
