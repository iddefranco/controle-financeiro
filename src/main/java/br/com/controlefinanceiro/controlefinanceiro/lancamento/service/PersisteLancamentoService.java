package br.com.controlefinanceiro.controlefinanceiro.lancamento.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoInvalidoExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotSaveException;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersisteLancamentoService {

	private final LancamentoRepository lancamentoRepository;

	private final LancamentoMapper lancamentoMapper;

	public LancamentoDto salvar(final LancamentoDto lancamentoDto) {
		log.info("M={} message={}", "salvar", "### Salvando lancamento ###");

		Optional.ofNullable(lancamentoDto)
				.orElseThrow(() -> new LancamentoInvalidoExcpetion("007"));

		return Optional.of(lancamentoDto)
				.map(lancamentoMapper::toEntity)
				.map(lancamentoRepository::save)
				.map(lancamentoMapper::toDto)
				.orElseThrow(() -> new LancamentoNotSaveException("005"));
	}


}
