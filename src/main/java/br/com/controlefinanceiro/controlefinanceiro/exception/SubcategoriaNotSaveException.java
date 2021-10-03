package br.com.controlefinanceiro.controlefinanceiro.exception;

public class SubcategoriaNotSaveException extends ServiceException {
	public SubcategoriaNotSaveException() {
	}

	public SubcategoriaNotSaveException(String codigo) {
		super.codigo = codigo;
	}
}
