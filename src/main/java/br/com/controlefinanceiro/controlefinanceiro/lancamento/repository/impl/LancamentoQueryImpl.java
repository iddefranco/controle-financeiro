package br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto.LancamentoFilterDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LancamentoQueryImpl implements LancamentoQuery {

	@PersistenceContext
	private final EntityManager manager;

	private final LancamentoMapper lancamentoMapper;


	@Override
	public Page<LancamentoDto> findLancamentoAll(LancamentoFilterDto lancamentoFilterDto, Pageable pageable) {
		String jpql = new StringBuilder()
				.append(" SELECT l ")
				.append(" FROM LancamentoEntity l ")
				.append(" WHERE l.data between :dataIni and :dataFim ")
				.append(temSubcategoria(lancamentoFilterDto) ? " AND l.subcategoria.id = :idSubcategoria " : "")
				.append(" order by :dataIni ").toString();

		TypedQuery<LancamentoEntity> query = this.manager.createQuery(jpql, LancamentoEntity.class);
		query.setParameter("dataIni", lancamentoFilterDto.getDataInicio());
		query.setParameter("dataFim", lancamentoFilterDto.getDataFim());
		if (temSubcategoria(lancamentoFilterDto))
			query.setParameter("idSubcategoria", lancamentoFilterDto.getIdSubcategoria());

		adicionarRestricaoPaginacao(query, pageable);
		List<LancamentoEntity> lancamentos = new ArrayList<>(query.getResultList());
		return new PageImpl<>(lancamentoMapper.toDto(lancamentos), pageable, total(lancamentoFilterDto));
	}

	private Boolean temSubcategoria(LancamentoFilterDto filterDto) {
		return filterDto.getIdSubcategoria() != null && filterDto.getIdSubcategoria() > 0;
	}

	private Long total(LancamentoFilterDto filterDto) {
		String jpql = new StringBuilder()
				.append(" SELECT count(l) ")
				.append(" FROM LancamentoEntity l ")
				.append(" WHERE l.data between :dataIni and :dataFim ")
				.append(temSubcategoria(filterDto) ? " AND l.subcategoria.id = :idSubcategoria " : "")
				.toString();

		TypedQuery<Long> query = this.manager.createQuery(jpql, Long.class);
		query.setParameter("dataIni", filterDto.getDataInicio());
		query.setParameter("dataFim", filterDto.getDataFim());
		if (temSubcategoria(filterDto))
			query.setParameter("idSubcategoria", filterDto.getIdSubcategoria());

		Long count = query.getSingleResult();
		return count;
	}

	private void adicionarRestricaoPaginacao(TypedQuery<LancamentoEntity> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegisto = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegisto);
		query.setMaxResults(totalRegistrosPorPagina);
	}
}
