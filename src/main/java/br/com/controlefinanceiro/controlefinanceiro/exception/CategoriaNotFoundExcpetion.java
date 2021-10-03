package br.com.controlefinanceiro.controlefinanceiro.exception;

import org.springframework.http.HttpStatus;

public class CategoriaNotFoundExcpetion extends ServiceException {

	public CategoriaNotFoundExcpetion() {
	}

	public CategoriaNotFoundExcpetion(String codigo) {
		super.codigo = codigo;
		super.httpStatus = HttpStatus.NOT_FOUND;
	}
}
