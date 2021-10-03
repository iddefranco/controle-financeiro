package br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BalancoDto {

	private CategoriaDto categoriaDto;

	private BigDecimal receita;

	private BigDecimal despesa;

	private BigDecimal saldo;
}
