package br.com.controlefinanceiro.controlefinanceiro.lancamento.dto;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.util.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
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

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate data;

	private SubcategoriaDto subcategoria;

}
