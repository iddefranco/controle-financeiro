package br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity;

import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "subcategoria")
@Builder
@ToString(exclude = "categoria")
@EqualsAndHashCode(exclude = "categoria ")
public class SubcategoriaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_subcategoria")
	private Long id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaEntity categoria;
}
