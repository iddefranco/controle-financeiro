package br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BalancoQueryImpl implements BalancoQuery {

	@PersistenceContext
	private final EntityManager manager;

	private final CategoriaMapper categoriaMapper;

	@Override
	public BalancoDto retornaBalanco(LocalDate dataIni, LocalDate dataFim, Long idCategoria) {
		Boolean temCategoria = this.temCategoria(idCategoria);

		String jpql = new StringBuilder()
				.append(" SELECT l ")
				.append(" FROM LancamentoEntity l ")
				.append(" WHERE l.data between :dataIni and :dataFim ")
				.append(temCategoria ? " AND l.subcategoria.categoria.id = :idCategoria " : "")
				.append(" order by :dataIni ").toString();

		TypedQuery<LancamentoEntity> query = this.manager.createQuery(jpql, LancamentoEntity.class);
		query.setParameter("dataIni", dataIni);
		query.setParameter("dataFim", dataFim);
		if (temCategoria)
			query.setParameter("idCategoria", idCategoria);

		List<LancamentoEntity> lancamentos = new ArrayList<>(query.getResultList());
		final BalancoDto balanco = this.convertLancamentoParaBalanco( lancamentos);

		return balanco;
	}

	private BalancoDto convertLancamentoParaBalanco(List<LancamentoEntity> lancamentos) {
		final var VALOR_NEGATIVO = -1;
		final var VALOR_POSITIVO = 0;

		BigDecimal despesa =  lancamentos.stream()
				.filter(item -> item.getValor().compareTo(BigDecimal.ZERO) == VALOR_NEGATIVO)
				.map(valor -> valor.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal receita =  lancamentos.stream()
				.filter(item -> item.getValor().compareTo(BigDecimal.ZERO) > VALOR_POSITIVO)
				.map(valor -> valor.getValor())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal saldo = receita.subtract(despesa.multiply(BigDecimal.valueOf(-1))).setScale(2);

		Optional<LancamentoEntity> categoriaOpt = lancamentos.stream().findFirst();

		return  BalancoDto.builder()
				.categoriaDto(categoriaOpt.isPresent() ? categoriaMapper.toDto(categoriaOpt.get()
						.getSubcategoria().getCategoria()) : null)
				.despesa(despesa)
				.receita(receita)
				.saldo(saldo)
				.build();
	}

	private Boolean temCategoria(Long idCategoria) {
		return idCategoria != null && idCategoria > 0;
	}


}
