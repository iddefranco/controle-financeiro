package br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.impl.SubcategoriaQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoriaRepository extends JpaRepository<SubcategoriaEntity, Long>, SubcategoriaQuery {
}
