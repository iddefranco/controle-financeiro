package br.com.controlefinanceiro.controlefinanceiro.http.advice.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@JsonInclude(NON_EMPTY)
public class ErrorResponse {

    private String codigo;

    private String mensagem;
}