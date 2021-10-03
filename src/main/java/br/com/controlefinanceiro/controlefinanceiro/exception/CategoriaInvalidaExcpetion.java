package br.com.controlefinanceiro.controlefinanceiro.exception;

public class CategoriaInvalidaExcpetion extends ServiceException {
	public CategoriaInvalidaExcpetion() {
	}

	public CategoriaInvalidaExcpetion(String codigo) {
		super.codigo = codigo;
	}
}
