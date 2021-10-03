package br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
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
public class SubcategoriaQueryImpl implements SubcategoriaQuery {
	@PersistenceContext
	private final EntityManager manager;

	private final SubcategoriaMapper subcategoriaMapper;

	@Override
	public Page<SubcategoriaEntity> findCategoriaAll(String nome, Pageable pageable) {
		String jpql = new StringBuilder()
				.append(" SELECT c ")
				.append(" FROM SubcategoriaEntity c ")
				.append(" WHERE 1=1 ")
				.append(temNome(nome) ? "and  nome like :nome " : "")
				.append(" order by nome ").toString();

		TypedQuery<SubcategoriaEntity> query = this.manager.createQuery(jpql, SubcategoriaEntity.class);
		if (temNome(nome))
			query.setParameter("nome", nome + "%");

		adicionarRestricaoPaginacao(query, pageable);
		List<SubcategoriaEntity> subcategorias = new ArrayList<>(query.getResultList());
		return new PageImpl<>(subcategorias, pageable, total(nome));
	}

	private Boolean temNome(String nome) {
		return nome != null && nome.trim().length() > 0;
	}

	private Long total(String nome) {
		String jpql = new StringBuilder()
				.append(" SELECT count(c) ")
				.append(" FROM SubcategoriaEntity c ")
				.append(" WHERE 1=1 ")
				.append(temNome(nome) ? "and  nome like :nome " : "")
				.toString();

		TypedQuery<Long> query = this.manager.createQuery(jpql, Long.class);

		if (temNome(nome))
			query.setParameter("nome", nome);

		Long count = query.getSingleResult();
		return count;
	}

	private void adicionarRestricaoPaginacao(TypedQuery<SubcategoriaEntity> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegisto = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegisto);
		query.setMaxResults(totalRegistrosPorPagina);
	}
}
