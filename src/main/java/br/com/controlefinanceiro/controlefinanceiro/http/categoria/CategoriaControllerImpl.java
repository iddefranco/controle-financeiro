package br.com.controlefinanceiro.controlefinanceiro.http.categoria;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.PersisteCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.RemoveCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.ConsultaCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.AtualizaCategoriaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
@Api(tags = "/v1/categorias", value = "Grupo de api para registar categorias")
public class CategoriaControllerImpl implements CategoriaController {

	private final PersisteCategoriaService persisteCategoriaService;

	private final AtualizaCategoriaService atualizaCategoriaService;

	private final RemoveCategoriaService removeCategoriaService;

	private final ConsultaCategoriaService consultaCategoriaService;

	@PostMapping
	public ResponseEntity<CategoriaDto> save(@Valid @RequestBody CategoriaDto categoriaDto) {
		final CategoriaDto categoriaResultDto = persisteCategoriaService.salvar(categoriaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaResultDto);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@Valid @PathVariable("id") Long id, @RequestBody CategoriaDto categoriaDto) {
		atualizaCategoriaService.atualizar(categoriaDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		removeCategoriaService.remover(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaDto> findById(@PathVariable("id") Long id) {
		final CategoriaDto categoriaDto = consultaCategoriaService.retornaCategoriaPorId(id);
		return ResponseEntity.ok(categoriaDto);
	}

	@GetMapping
	public Page<CategoriaDto> findAll(@RequestParam(required = false) String nome, @PageableDefault(sort = "id", page = 0, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
		return consultaCategoriaService.retornaTodasCategoria(nome, pageable);
	}
}
