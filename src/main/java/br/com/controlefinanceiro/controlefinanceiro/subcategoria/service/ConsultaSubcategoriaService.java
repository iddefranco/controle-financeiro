package br.com.controlefinanceiro.controlefinanceiro.subcategoria.service;

import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaSubcategoriaService {

	private final SubcategoriaRepository subcategoriaRepository;

	private final SubcategoriaMapper subcategoriaMapper;

	public SubcategoriaDto retornaCategoriaPorId(final Long idCategoria) {
		log.info("M={} message={}", "retornaCategoriaPorId", "### consultando subcategoria por id ###");
		return this.subcategoriaRepository.findById(idCategoria)
				.map(subcategoriaMapper::toDto)
				.orElseThrow(() -> new SubcategoriaNotFoundExcpetion("006"));
	}

	public Page<SubcategoriaDto> retornaTodasCategoria(String nome, Pageable pageable) {
		log.info("M={} message={}", "retornaTodasCategoria", "### lista subcategoria ###");
		return this.subcategoriaRepository.findCategoriaAll(nome, pageable).map(subcategoriaMapper::toDto);
	}
}
