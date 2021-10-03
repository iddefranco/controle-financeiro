package br.com.controlefinanceiro.controlefinanceiro.categoria.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaQuery {
	Page<CategoriaEntity> findCategoriaAll(String nome, Pageable pageable);
}
