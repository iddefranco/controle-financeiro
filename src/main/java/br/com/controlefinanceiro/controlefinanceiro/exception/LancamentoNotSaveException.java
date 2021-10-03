package br.com.controlefinanceiro.controlefinanceiro.exception;

public class LancamentoNotSaveException extends ServiceException {
	public LancamentoNotSaveException() {
	}

	public LancamentoNotSaveException(String codigo) {
		super.codigo = codigo;
	}
}
