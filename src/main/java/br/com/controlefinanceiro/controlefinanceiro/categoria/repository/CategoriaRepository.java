package br.com.controlefinanceiro.controlefinanceiro.categoria.repository;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.impl.CategoriaQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long>, CategoriaQuery {

}
