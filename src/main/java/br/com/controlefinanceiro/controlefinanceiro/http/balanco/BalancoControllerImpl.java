package br.com.controlefinanceiro.controlefinanceiro.http.balanco;

import br.com.controlefinanceiro.controlefinanceiro.balanco.BalancoService;
import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoDto;
import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoFilterDto;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/balanco")
@RequiredArgsConstructor
@Api(tags = "/v1/balanco", value = "Grupo de api para retorna o balanço")
public class BalancoControllerImpl implements BalancoController {

	private final BalancoService persisteLancamentoService;

	@GetMapping
	public BalancoDto getBalanco(@Valid BalancoFilterDto balancoFilterDto) {

		return persisteLancamentoService.retornaBalanco(balancoFilterDto.getDataInicio(),
				balancoFilterDto.getDataFim(), balancoFilterDto.getIdCategoria());
	}


}
