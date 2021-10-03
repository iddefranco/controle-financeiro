package br.com.controlefinanceiro.controlefinanceiro.lancamento.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveLancamentoService {
	private final LancamentoRepository lancamentoRepository;

	public void remover(Long idLancamento) {
		log.info("M={} message={}", "remover", "### removendo lancamento ###");
		final var categoria = this.lancamentoRepository.findById(idLancamento)
				.orElseThrow(() -> new LancamentoNotFoundExcpetion("009"));

		this.lancamentoRepository.delete(categoria);
	}
}

