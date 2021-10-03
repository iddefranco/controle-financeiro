package br.com.controlefinanceiro.controlefinanceiro.exception;

public class LancamentoInvalidoExcpetion extends ServiceException {
	public LancamentoInvalidoExcpetion() {
	}

	public LancamentoInvalidoExcpetion(String codigo) {
		super.codigo = codigo;
	}
}
