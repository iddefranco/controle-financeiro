package br.com.controlefinanceiro.controlefinanceiro.lancamento.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto.LancamentoFilterDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaLancamentoService {

	private final LancamentoRepository lancamentoRepository;

	private final LancamentoMapper lancamentoMapper;

	public LancamentoDto retornaLancamentoPorId(final Long idCategoria) {
		log.info("M={} message={}", "retornaLancamentoPorId", "### consulta lancamento por id ###");
		return this.lancamentoRepository.findById(idCategoria)
				.map(lancamentoMapper::toDto)
				.orElseThrow(() -> new LancamentoNotFoundExcpetion("009"));
	}

	public Page<LancamentoDto> retornaTodosLancamento(LancamentoFilterDto lancamentoFilterDto, Pageable pageable) {
		log.info("M={} message={}", "retornaTodosLancamento", "### lista lancamento ###");
		return this.lancamentoRepository.findLancamentoAll(lancamentoFilterDto, pageable);
	}
}