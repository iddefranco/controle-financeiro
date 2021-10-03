package br.com.controlefinanceiro.controlefinanceiro.subcategoria.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaInvalidaExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaNotSaveException;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtualizaSubcategoriaService {
	private final SubcategoriaRepository subcategoriaRepository;

	private final SubcategoriaMapper subcategoriaMapper;

	public SubcategoriaDto atualizar(final SubcategoriaDto subcategoriaDto, final Long idSubcategoria) {
		log.info("M={} message={}", "atualizar", "### atualizando subcategoria ###");
		final var subcategoriaEntity = subcategoriaRepository.findById(idSubcategoria)
				.orElseThrow(() -> new SubcategoriaInvalidaExcpetion("006"));
		BeanUtils.copyProperties(subcategoriaDto, subcategoriaEntity, "id");
		return Optional.ofNullable(subcategoriaEntity)
				.map(subcategoriaRepository::save)
				.map(subcategoriaMapper::toDto)
				.orElseThrow(() -> new SubcategoriaNotSaveException("005"));
	}


}
