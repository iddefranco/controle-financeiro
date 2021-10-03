package br.com.controlefinanceiro.controlefinanceiro.exception;

import org.springframework.http.HttpStatus;

public class LancamentoNotFoundExcpetion extends ServiceException {

	public LancamentoNotFoundExcpetion() {
	}

	public LancamentoNotFoundExcpetion(String codigo) {
		super.codigo = codigo;
		super.httpStatus = HttpStatus.NOT_FOUND;
	}
}
