package br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto.LancamentoFilterDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoQuery {

	Page<LancamentoDto> findLancamentoAll(LancamentoFilterDto lancamentoFilterDto, Pageable pageable);
}
