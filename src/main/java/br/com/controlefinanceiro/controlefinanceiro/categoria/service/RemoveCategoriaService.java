package br.com.controlefinanceiro.controlefinanceiro.categoria.service;

import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotFoundExcpetion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveCategoriaService {

	private final CategoriaRepository categoriaRepository;

	public void remover(Long idCategoria) {
		log.info("M={} message={}", "remover", "### remove subcategoria ###");
		final var categoria = this.categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new CategoriaNotFoundExcpetion("002"));

		log.info("M={} message={}", "remover", "### Remvendo Categoria ###");
		this.categoriaRepository.delete(categoria);
	}
}

