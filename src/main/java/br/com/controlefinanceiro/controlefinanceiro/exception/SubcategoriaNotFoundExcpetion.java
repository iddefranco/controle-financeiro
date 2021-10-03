package br.com.controlefinanceiro.controlefinanceiro.exception;

import org.springframework.http.HttpStatus;

public class SubcategoriaNotFoundExcpetion extends ServiceException {

	public SubcategoriaNotFoundExcpetion() {
	}

	public SubcategoriaNotFoundExcpetion(String codigo) {
		super.codigo = codigo;
		super.httpStatus = HttpStatus.NOT_FOUND;
	}
}
