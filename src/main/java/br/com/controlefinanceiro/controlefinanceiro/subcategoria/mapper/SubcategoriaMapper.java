package br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper;

import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface SubcategoriaMapper {

	@Mapping(source = "nome", target = "nome")
	@Mapping(source = "categoria", target = "categoria")
	SubcategoriaEntity toEntity(final SubcategoriaDto subcategoriaDto);

	SubcategoriaDto toDto(final SubcategoriaEntity subcategoria);
}
