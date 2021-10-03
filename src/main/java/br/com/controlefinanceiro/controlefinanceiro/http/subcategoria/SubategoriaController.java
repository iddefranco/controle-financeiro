package br.com.controlefinanceiro.controlefinanceiro.http.subcategoria;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SubategoriaController {
	@ApiOperation(value = "API criada para gravar uma subcategoria", response = SubcategoriaDto.class)
	@ApiResponses(value = {@ApiResponse(code = 201, message = "Retorno da criação da subcategoria", response = CategoriaDto.class),
			@ApiResponse(code = 400, message = "Erro ao cadastar uma subcategoria")
	})
	@PostMapping
	ResponseEntity<SubcategoriaDto> save(@RequestBody SubcategoriaDto subcategoriaDto);

	@ApiOperation(value = "API criada para atualizar uma subcategoria")
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Retorno da atualização de subcategoria"),
			@ApiResponse(code = 404, message = "Recurso não encontrado"),
			@ApiResponse(code = 500, message = "Erro ao cadastar uma subcategoria")}
	)
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void update(@Valid @PathVariable("id") Long id, @RequestBody SubcategoriaDto subcategoriaDto);


	@ApiOperation(value = "API criada para excluir uma subcategoria")
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Retorna da exclusão com sucesso"),
			@ApiResponse(code = 404, message = "Recurso não encontrao"),
			@ApiResponse(code = 500, message = "Erro ao cadastar uma subcategoria")
	})
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void delete(@PathVariable("id") Long id);

	@ApiOperation(value="API criada para listar uma subcategoria")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Retorna uma subcateria por id"),
			@ApiResponse(code = 404, message = "Recurso não encontrao"),
			@ApiResponse(code = 500, message = "Erro ao consultar uma subcategoria")
	})
	@GetMapping("/{id}")
	ResponseEntity<SubcategoriaDto> findById(@PathVariable("id") Long id);

	@GetMapping
	@ApiOperation(value="API criada para listar todas subcategorias")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Retorna lista de subcategorias"),
			@ApiResponse(code = 500, message = "Erro ao consultar uma subcategorias")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "0", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "20", value = "Number of records per page."),
			@ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). "
					+ "Default sort order is ascending. " + "Multiple sort criteria are supported.")})
	Page<SubcategoriaDto> findAll(@RequestParam(required = false) String nome,
								  @PageableDefault(sort = "id", page = 0, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

}
