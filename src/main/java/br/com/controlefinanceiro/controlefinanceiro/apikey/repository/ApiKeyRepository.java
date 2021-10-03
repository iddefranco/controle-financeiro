package br.com.controlefinanceiro.controlefinanceiro.apikey.repository;

import br.com.controlefinanceiro.controlefinanceiro.apikey.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {
    public Optional<ApiKey> findOneByKey(String key);
}