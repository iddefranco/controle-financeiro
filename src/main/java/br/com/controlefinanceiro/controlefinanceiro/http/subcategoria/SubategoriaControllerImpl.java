package br.com.controlefinanceiro.controlefinanceiro.http.subcategoria;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.AtualizaSubcategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.ConsultaSubcategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.PersisteSubcategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.RemoveSubcategoriaService;
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
@RequestMapping("/v1/subcategorias")
@RequiredArgsConstructor
@Api(tags = "/v1/subcategorias", value = "Grupo de api para registar Subcategorias")
public class SubategoriaControllerImpl implements SubategoriaController {

	private final PersisteSubcategoriaService persisteSubcategoriaService;

	private final AtualizaSubcategoriaService atualizaSubcategoriaService;

	private final RemoveSubcategoriaService removeSubcategoriaService;

	private final ConsultaSubcategoriaService consultaSubcategoriaService;

	@PostMapping
	public ResponseEntity<SubcategoriaDto> save(@Valid @RequestBody SubcategoriaDto subcategoriaDto) {
		final SubcategoriaDto subcategoriaResultDto = persisteSubcategoriaService.salvar(subcategoriaDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(subcategoriaResultDto);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@Valid @PathVariable("id") Long id, @RequestBody SubcategoriaDto subcategoriaDto) {
		atualizaSubcategoriaService.atualizar(subcategoriaDto, id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		removeSubcategoriaService.remover(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubcategoriaDto> findById(@PathVariable("id") Long id) {
		final SubcategoriaDto subcategoriaDto = consultaSubcategoriaService.retornaCategoriaPorId(id);
		return ResponseEntity.ok(subcategoriaDto);
	}

	@GetMapping
	public Page<SubcategoriaDto> findAll(@RequestParam(required = false) String nome,
										 @PageableDefault(sort = "id", page = 0, direction = Sort.Direction.DESC, size = 20) Pageable pageable) {
		return consultaSubcategoriaService.retornaTodasCategoria(nome, pageable);
	}
}
