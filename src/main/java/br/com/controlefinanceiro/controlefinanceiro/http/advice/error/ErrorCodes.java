package br.com.controlefinanceiro.controlefinanceiro.http.advice.error;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
final class ErrorCodes {

    static final String DID_NOT_PASS_ANY_FILTER = "DID_NOT_PASS_ANY_FILTER";

    static final String INVALID_CUSTOMER_TO_INSTALLMENT = "INVALID_CUSTOMER_TO_INSTALLMENT";

}