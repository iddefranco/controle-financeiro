package br.com.controlefinanceiro.controlefinanceiro.lancamento.entity;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "lancamento")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_lancamento")
	private Long id;

	private String comentario;

	@NotNull
	private BigDecimal valor;

	@NotNull
	private LocalDate data;

	@ManyToOne
	@JoinColumn(name = "id_subcategoria")
	private SubcategoriaEntity subcategoria;

}
