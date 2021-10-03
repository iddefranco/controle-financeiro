package br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.impl;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubcategoriaQuery {
	Page<SubcategoriaEntity> findCategoriaAll(String nome, Pageable pageable);
}
