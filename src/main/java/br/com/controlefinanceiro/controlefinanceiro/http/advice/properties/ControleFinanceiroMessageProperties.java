package br.com.controlefinanceiro.controlefinanceiro.http.advice.properties;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "controle.financeiro")
public final class ControleFinanceiroMessageProperties {

    @Getter
    private final Map<String, String> messages;

}