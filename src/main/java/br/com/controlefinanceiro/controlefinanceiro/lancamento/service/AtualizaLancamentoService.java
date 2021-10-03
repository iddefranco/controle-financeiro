package br.com.controlefinanceiro.controlefinanceiro.lancamento.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotSaveException;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizaLancamentoService {
	private final LancamentoRepository lancamentoRepository;

	private final LancamentoMapper lancamentoMapper;

	public LancamentoDto atualizar(final LancamentoDto lancamentoDto, final Long idLancamento) {
		log.info("M={} message={}", "atualizar", "### Atualizando lancamento ###");
		final var lancamentoEntity = lancamentoRepository.findById(idLancamento)
				.orElseThrow(() -> new LancamentoNotFoundExcpetion("009"));

		BeanUtils.copyProperties(lancamentoDto, lancamentoEntity, "id");

		return Optional.ofNullable(lancamentoEntity)
				.map(lancamentoRepository::save)
				.map(lancamentoMapper::toDto)
				.orElseThrow(() -> new LancamentoNotSaveException("008"));
	}


}
