package br.com.controlefinanceiro.controlefinanceiro.categoria.service;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaInvalidaExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotSaveException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersisteCategoriaService {
	private final CategoriaRepository categoriaRepository;

	private final CategoriaMapper categoriaMapper;

	public CategoriaDto salvar(final CategoriaDto categoriaDto) {
		log.info("M={} message={}", "salvar", "### Salvando Categoria ###");
		Optional.ofNullable(categoriaDto)
				.orElseThrow(() -> new CategoriaInvalidaExcpetion("003"));

		return Optional.of(categoriaDto)
				.map(categoriaMapper::toEntity)
				.map(categoriaRepository::save)
				.map(categoriaMapper::toDto)
				.orElseThrow(() -> new CategoriaNotSaveException("001"));
	}


}
