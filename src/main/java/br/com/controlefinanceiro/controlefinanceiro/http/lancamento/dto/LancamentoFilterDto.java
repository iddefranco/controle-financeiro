package br.com.controlefinanceiro.controlefinanceiro.http.lancamento.dto;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import java.time.LocalDate;
import java.util.Date;
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
public class LancamentoFilterDto {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date dataInicio;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate dataFim;

	private Long idSubcategoria;



}