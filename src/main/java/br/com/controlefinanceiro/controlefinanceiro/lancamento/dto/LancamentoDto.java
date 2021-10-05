package br.com.controlefinanceiro.controlefinanceiro.lancamento.dto;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDto {

	private Long id;

	private String comentario;

	private BigDecimal valor;

	private LocalDate data;

	private SubcategoriaDto subcategoria;

}
