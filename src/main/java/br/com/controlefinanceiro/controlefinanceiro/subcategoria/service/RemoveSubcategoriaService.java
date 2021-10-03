package br.com.controlefinanceiro.controlefinanceiro.subcategoria.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoveSubcategoriaService {
	private final SubcategoriaRepository subcategoriaRepository;

	public void remover(Long idSubcategoria) {
		log.info("M={} message={}", "remover", "### removendo subcategoria ###");
		final var categoria = this.subcategoriaRepository.findById(idSubcategoria)
				.orElseThrow(() -> new SubcategoriaNotFoundExcpetion("006"));

		this.subcategoriaRepository.delete(categoria);
	}
}

