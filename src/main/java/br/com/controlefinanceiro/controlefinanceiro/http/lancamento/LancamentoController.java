package br.com.controlefinanceiro.controlefinanceiro.http.lancamento;

import br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto.LancamentoFilterDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseStatus;

public interface LancamentoController {
	@ApiOperation(value = "API criada para gravar uma lançamentos", response = LancamentoDto.class)
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Retorno da criação da lançamentos", response = LancamentoDto.class),
			@ApiResponse(code = 400, message = "Erro ao cadastar uma lançamentos")
	})
	@PostMapping
	ResponseEntity<LancamentoDto> save(@RequestBody LancamentoDto lancamentoDto);

	@ApiOperation(value = "API criada para atualizar um lançamento")
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Retorno da atualização de lançamento"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro ao cadastar um lançamento")}
	)
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@Valid @PathVariable("id") Long id, @RequestBody LancamentoDto lancamentoDto);


	@ApiOperation(value = "API criada para excluir um lançamento")
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Retorna da exclusão com sucesso"),
			@ApiResponse(code = 404, message = "Recurso não encontrao"),
			@ApiResponse(code = 500, message = "Erro ao cadastar um lançamento")
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable("id") Long id);

	@ApiOperation(value="API criada para listar uma lançamento")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Retorna lançamento por Id"),
			@ApiResponse(code = 404, message = "Recurso não encontrao"),
			@ApiResponse(code = 500, message = "Erro ao consultar um lançamento")
	})
	@GetMapping("/{id}")
	ResponseEntity<LancamentoDto> findById(@PathVariable("id") Long id);

	@GetMapping
	@ApiOperation(value="API criada para listar todos lançamento")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Retorna lista de lançamentos"),
			@ApiResponse(code = 500, message = "Erro ao consultar um lançamento")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "20", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
	Page<LancamentoDto> findAll(@Valid LancamentoFilterDto lancamentoFilterDto,
								@PageableDefault(sort = "id", page = 0, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

}
