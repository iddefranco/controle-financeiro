package br.com.controlefinanceiro.controlefinanceiro.subcategoria.component;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;

public class SubcategoriaComponent {

	public static SubcategoriaDto createSubcategoriaDto(Long id, String nome, CategoriaDto categoriaDto) {
		return SubcategoriaDto.builder()
				.id(id)
				.nome(nome)
				.categoria(categoriaDto)
				.build();
	}

	public static SubcategoriaEntity createSubcategoriaEntity(Long id, String nome, CategoriaEntity categoriaEntity) {
		return SubcategoriaEntity.builder().
				id(id)
				.nome(nome)
				.categoria(categoriaEntity)
				.build();

	}
}
