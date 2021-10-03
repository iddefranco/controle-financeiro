package br.com.controlefinanceiro.controlefinanceiro.lancamento.component;

import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LancamentoComponent {

	public static LancamentoDto createLancamentoDto(Long id, String comentario, BigDecimal valor, LocalDate data, SubcategoriaDto subcategoriaDto) {
		return LancamentoDto.builder()
				.id(id)
				.comentario(comentario)
				.valor(valor)
				.data(data)
				.subcategoria(subcategoriaDto)
				.build();
	}

	public static LancamentoEntity createSLancamentoEntity(Long id, String comentario, BigDecimal valor, LocalDate data, SubcategoriaEntity subcategoriaDto) {
		return LancamentoEntity.builder()
				.id(id)
				.comentario(comentario)
				.valor(valor)
				.data(data)
				.subcategoria(subcategoriaDto)
				.build();

	}
}
