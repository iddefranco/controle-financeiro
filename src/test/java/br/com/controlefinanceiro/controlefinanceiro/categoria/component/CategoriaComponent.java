package br.com.controlefinanceiro.controlefinanceiro.categoria.component;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;

public class CategoriaComponent {

	public static CategoriaDto createCategoriaDto(Long id, String nome) {
		return CategoriaDto.builder()
				.id(id)
				.nome(nome)
				.build();
	}

	public static CategoriaEntity createCategoriaEntity(Long id, String nome) {
		return CategoriaEntity.builder().
				id(id)
				.nome(nome)
				.build();

	}
}
