package br.com.controlefinanceiro.controlefinanceiro.http.advice.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ErrorResponseWrapper {

    @Singular
    private List<ErrorResponse> errors;
}