package br.com.controlefinanceiro.controlefinanceiro.categoria.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {
	private Long id;

	@NotBlank
	@NotNull
	private String nome;

}
