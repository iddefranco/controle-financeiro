package br.com.controlefinanceiro.controlefinanceiro.lancamento.repository;

import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl.BalancoQuery;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.impl.LancamentoQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<LancamentoEntity, Long>, BalancoQuery, LancamentoQuery {


}
