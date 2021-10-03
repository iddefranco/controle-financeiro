package br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubcategoriaDto {
	private Long id;

	private String nome;

	private CategoriaDto categoria;
}
