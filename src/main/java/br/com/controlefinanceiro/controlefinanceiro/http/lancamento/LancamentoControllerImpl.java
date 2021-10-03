package br.com.controlefinanceiro.controlefinanceiro.http.lancamento;

import br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto.LancamentoFilterDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.AtualizaLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.ConsultaLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.PersisteLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.RemoveLancamentoService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/lancamentos")
@RequiredArgsConstructor
@Api(tags = "/v1/lancamentos", value = "Grupo de api para registar Lançamentos")
public class LancamentoControllerImpl implements LancamentoController {

	private final PersisteLancamentoService persisteLancamentoService;

	private final AtualizaLancamentoService atualizaLancamentoService;

	private final RemoveLancamentoService removeLancamentoService;

	private final ConsultaLancamentoService consultaLancamentoService;

	@PostMapping
	public ResponseEntity<LancamentoDto> save(@Valid @RequestBody LancamentoDto lancamentoDto) {
		final LancamentoDto lancamentoResultDto = persisteLancamentoService.salvar(lancamentoDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoResultDto);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@Valid @PathVariable("id") Long id, @RequestBody LancamentoDto lancamentoDto) {
		atualizaLancamentoService.atualizar(lancamentoDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		removeLancamentoService.remover(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LancamentoDto> findById(@PathVariable("id") Long id) {
		final LancamentoDto lancamentoDto = consultaLancamentoService.retornaLancamentoPorId(id);
		return ResponseEntity.ok(lancamentoDto);
	}

	@GetMapping
	public Page<LancamentoDto> findAll(@Valid LancamentoFilterDto lancamentoFilterDto,
										   @PageableDefault(sort = "id", page = 0, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
		return consultaLancamentoService.retornaTodosLancamento(lancamentoFilterDto, pageable);
	}
}
