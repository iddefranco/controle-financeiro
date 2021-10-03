package br.com.controlefinanceiro.controlefinanceiro.categoria.entity;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "categoria")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CategoriaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_categoria")
	private Long id;

	@NotBlank
	@NotNull
	private String nome;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<SubcategoriaEntity> subcategorias;
}
