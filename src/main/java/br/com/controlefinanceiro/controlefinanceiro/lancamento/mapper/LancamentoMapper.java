package br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper;

import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface LancamentoMapper {

	@Mapping(source = "valor", target = "valor")
	@Mapping(source = "data", target = "data")
	@Mapping(source = "comentario", target = "comentario")
	@Mapping(source = "subcategoria", target = "subcategoria")
	LancamentoEntity toEntity(final LancamentoDto lancamentoDto);

	LancamentoDto toDto(final LancamentoEntity lancamento);

	List<LancamentoDto> toDto(List<LancamentoEntity> lancamentos);
}
