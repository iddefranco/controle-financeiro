package br.com.controlefinanceiro.controlefinanceiro.exception;

public class CategoriaNotSaveException extends ServiceException {
	public CategoriaNotSaveException() {
	}

	public CategoriaNotSaveException(String codigo) {
		super.codigo = codigo;
	}
}
