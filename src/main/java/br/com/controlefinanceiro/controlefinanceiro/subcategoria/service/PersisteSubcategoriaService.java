package br.com.controlefinanceiro.controlefinanceiro.subcategoria.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaInvalidaExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotSaveException;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersisteSubcategoriaService {
	private final SubcategoriaRepository subcategoriaRepository;

	private final SubcategoriaMapper subcategoriaMapper;

	public SubcategoriaDto salvar(final SubcategoriaDto subcategoriaDto) {
		log.info("M={} message={}", "salvar", "### Salvando subcategoria ###");

		Optional.ofNullable(subcategoriaDto)
				.orElseThrow(() -> new SubcategoriaInvalidaExcpetion("007"));

		return Optional.of(subcategoriaDto)
				.map(subcategoriaMapper::toEntity)
				.map(subcategoriaRepository::save)
				.map(subcategoriaMapper::toDto)
				.orElseThrow(() -> new LancamentoNotSaveException("005"));
	}


}
