package br.com.controlefinanceiro.controlefinanceiro.http.balanco.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalancoFilterDto {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dataInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dataFim;

	private Long idCategoria;

}