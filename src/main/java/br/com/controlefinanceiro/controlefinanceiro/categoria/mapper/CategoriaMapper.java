package br.com.controlefinanceiro.controlefinanceiro.categoria.mapper;

import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface CategoriaMapper {

	@Mapping(source = "nome", target = "nome")
	CategoriaEntity toEntity(final CategoriaDto categoriaDto);

	CategoriaDto toDto(final CategoriaEntity categoria);
}
