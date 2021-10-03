package br.com.controlefinanceiro.controlefinanceiro.categoria.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
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
public class CategoriaQueryImpl implements CategoriaQuery {
	@PersistenceContext
	private final EntityManager manager;

	private final CategoriaMapper categoriaMapper;

	@Override
	public Page<CategoriaEntity> findCategoriaAll(String nome, Pageable pageable) {
		String jpql = new StringBuilder()
				.append(" SELECT c ")
				.append(" FROM CategoriaEntity c ")
				.append(" WHERE 1=1 ")
				.append(temNome(nome) ? "and  nome like :nome " : "")
				.append(" order by nome ").toString();

		TypedQuery<CategoriaEntity> query = this.manager.createQuery(jpql, CategoriaEntity.class);
		if (temNome(nome))
			query.setParameter("nome", nome + "%");

		adicionarRestricaoPaginacao(query, pageable);
		List<CategoriaEntity> categorias = new ArrayList<>(query.getResultList());
		return new PageImpl<>(categorias, pageable, total(nome));
	}

	private Boolean temNome(String nome) {
		return nome != null && nome.trim().length() > 0;
	}

	private Long total(String nome) {
		String jpql = new StringBuilder()
				.append(" SELECT count(c) ")
				.append(" FROM CategoriaEntity c ")
				.append(" WHERE 1=1 ")
				.append(temNome(nome) ? "and  nome like :nome " : "")
				.toString();

		TypedQuery<Long> query = this.manager.createQuery(jpql, Long.class);

		if (temNome(nome))
			query.setParameter("nome", nome);

		Long count = query.getSingleResult();
		return count;
	}

	private void adicionarRestricaoPaginacao(TypedQuery<CategoriaEntity> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegisto = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegisto);
		query.setMaxResults(totalRegistrosPorPagina);
	}
}
