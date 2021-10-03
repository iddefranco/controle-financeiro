package br.com.controlefinanceiro.controlefinanceiro.http.balanco;

import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoDto;
import br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto.BalancoFilterDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

public interface BalancoController {


	@GetMapping
	@ApiOperation(value="API criada para listar o balanço")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Retorna lista de balanços"),
			@ApiResponse(code = 500, message = "Erro ao consultar um balanços")
	})
	BalancoDto getBalanco(BalancoFilterDto balancoFilterDto);

}
