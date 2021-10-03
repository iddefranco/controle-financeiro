package br.com.controlefinanceiro.controlefinanceiro.categoria.service;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotFoundExcpetion;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultaCategoriaService {

	private final CategoriaRepository categoriaRepository;

	private final CategoriaMapper categoriaMapper;

	public CategoriaDto retornaCategoriaPorId(final Long idCategoria) {
		log.info("M={} message={}", "retornaCategoriaPorId", "### Consultando Categoria por id ###");
		return this.categoriaRepository.findById(idCategoria)
				.map(categoriaMapper::toDto)
				.orElseThrow(() -> new CategoriaNotFoundExcpetion("002"));
	}

	public Page<CategoriaDto> retornaTodasCategoria(String nome, Pageable pageable) {
		log.info("M={} message={}", "retornaTodasCategoria", "### Consultando todas Categoria ###");
		return this.categoriaRepository.findCategoriaAll(nome, pageable).map(categoriaMapper::toDto);
	}
}
