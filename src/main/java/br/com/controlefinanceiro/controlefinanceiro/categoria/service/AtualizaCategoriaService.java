package br.com.controlefinanceiro.controlefinanceiro.categoria.service;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotSaveException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizaCategoriaService {
	private final CategoriaRepository categoriaRepository;

	private final CategoriaMapper categoriaMapper;

	public CategoriaDto atualizar(final CategoriaDto categoriaDto, final Long idCategoria) {

		final var categoriaEntity = categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new CategoriaNotFoundExcpetion("002"));

		BeanUtils.copyProperties(categoriaDto, categoriaEntity, "id");
		log.info("M={} message={}", "atualizar", "### Atualizando Categoria ###");
		return Optional.ofNullable(categoriaEntity)
				.map(categoriaRepository::save)
				.map(categoriaMapper::toDto)
				.orElseThrow(() -> new CategoriaNotSaveException("001"));
	}


}
