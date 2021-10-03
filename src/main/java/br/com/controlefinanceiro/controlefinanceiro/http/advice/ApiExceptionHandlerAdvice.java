package br.com.controlefinanceiro.controlefinanceiro.http.advice;

import br.com.controlefinanceiro.controlefinanceiro.exception.ServiceException;
import br.com.controlefinanceiro.controlefinanceiro.http.advice.error.ErrorResponse;
import br.com.controlefinanceiro.controlefinanceiro.http.advice.properties.ControleFinanceiroMessageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

	private static final Object[] MESSAGE_DEFAULT_ARGS_EMPTY = new Object[]{};
	private static final HttpHeaders DEFAULT_HTTP_HEADERS = new HttpHeaders();

	private static final String PLACEHOLDER_PATTERN = ".*\\{\\d+}.*";

	private static final String CODE_VALIDATION_ERROR = "validation.error";

	private final ControleFinanceiroMessageProperties controleFinanceiroMessageProperties;

	private static void logException(final String methodName, final HttpStatus httpStatus, final Exception e) {
		log.error("M={} status={} message={}", methodName, httpStatus.value(), e.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logException("handleMethodArgumentNotValid", HttpStatus.BAD_REQUEST, ex);
		final var errorResponse = ErrorResponse.builder()
				.codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
				.mensagem("O campo " + "'" + ex.getFieldError().getField() + "'" + " " + ex.getFieldError().getDefaultMessage())
				.build();

		return this.handleExceptionInternal(ex, errorResponse, DEFAULT_HTTP_HEADERS, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleMethodDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
		logException("handleMethodDataIntegrityViolation", HttpStatus.BAD_REQUEST, ex);

		final var errorResponse = ErrorResponse.builder()
				.codigo("004")
				.mensagem(this.controleFinanceiroMessageProperties.getMessages().get("004"))
				.build();

		return this.handleExceptionInternal(ex, errorResponse, DEFAULT_HTTP_HEADERS, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Object> handlerService(final ServiceException ex, final WebRequest request) {
		final HttpStatus httpStatus = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST;
		logException("handlerService", httpStatus, ex);
		final var errorResponse = ErrorResponse.builder()
				.codigo(ex.getCodigo())
				.mensagem(this.controleFinanceiroMessageProperties.getMessages().get(ex.getCodigo()))
				.build();

		return this.handleExceptionInternal(ex, errorResponse, DEFAULT_HTTP_HEADERS, httpStatus, request);
	}
}
