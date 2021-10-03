package br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoDto;
import java.time.LocalDate;

public interface BalancoQuery {

	BalancoDto retornaBalanco(LocalDate dataIni, LocalDate dataFim, Long idCategoria);
}
