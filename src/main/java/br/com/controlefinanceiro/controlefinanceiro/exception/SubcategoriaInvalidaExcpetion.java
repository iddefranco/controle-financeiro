package br.com.controlefinanceiro.controlefinanceiro.exception;

public class SubcategoriaInvalidaExcpetion extends ServiceException {
	public SubcategoriaInvalidaExcpetion() {
	}

	public SubcategoriaInvalidaExcpetion(String codigo) {
		super.codigo = codigo;
	}
}
