package br.com.controlefinanceiro.controlefinanceiro.lancamento.unit;


import br.com.controlefinanceiro.controlefinanceiro.categoria.component.CategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoInvalidoExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.component.LancamentoComponent;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.AtualizaLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.ConsultaLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.service.PersisteLancamentoService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.component.SubcategoriaComponent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LancamentoServiceTest {
	@InjectMocks
	private PersisteLancamentoService persisteLancamentoService;

	@InjectMocks
	private AtualizaLancamentoService atualizaLancamentoService;

	@InjectMocks
	private ConsultaLancamentoService consultaLancamentoService;

	@Mock
	private LancamentoRepository lancamentoRepository;

	@Mock
	private LancamentoMapper lancamentoMapper;

	@Test
	public void salva_lancamento_com_sucesso() {

		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var categoriaEntity = CategoriaComponent.createCategoriaEntity(1L, "Despesa Fixa");

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);
		final var subcategoriaEntity = SubcategoriaComponent.createSubcategoriaEntity(1L, "Despesas com Educação", categoriaEntity);

		final var lancamentoDto = LancamentoComponent.createLancamentoDto(null, "Despesas com Educação",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaDto);
		final var lancamentoResultDto = LancamentoComponent.createLancamentoDto(1L, "Despesas com Educação",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaDto);
		final var lancamentoEntity = LancamentoComponent.createSLancamentoEntity(null, "Despesas com Educação",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaEntity);
		final var lancamentoResultEntity = LancamentoComponent.createSLancamentoEntity(1L, "Despesas com Educação",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaEntity);

		when(lancamentoMapper.toEntity(lancamentoDto)).thenReturn(lancamentoEntity);
		when(lancamentoRepository.save(lancamentoEntity)).thenReturn(lancamentoResultEntity);
		when(lancamentoMapper.toDto(lancamentoResultEntity)).thenReturn(lancamentoResultDto);

		final var result = this.persisteLancamentoService.salvar(lancamentoDto);
		assertThat(result, is(not(nullValue())));
		assertThat(result.getId(), is(not(nullValue())));
	}

	@Test
	public void tenta_salvar_uma_lancamento_invalido() {
		LancamentoDto lancamentoDto = null;
		Assertions.assertThrows(LancamentoInvalidoExcpetion.class, () -> this.persisteLancamentoService.salvar(lancamentoDto));
	}

	@Test
	public void atualiza_um_lancamento() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var categoriaEntity = CategoriaComponent.createCategoriaEntity(1L, "Despesa Fixa");

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);
		final var subcategoriaEntity = SubcategoriaComponent.createSubcategoriaEntity(1L, "Despesas com Educação", categoriaEntity);


		final var lancamentoDto = LancamentoComponent.createLancamentoDto(1L, "Despesas com cursos",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaDto);

		final var lancamentoResultDto = LancamentoComponent.createLancamentoDto(1L, "Despesas com cursos",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaDto);

		final var lancamentoEntity = LancamentoComponent.createSLancamentoEntity(1L, "Despesas com Educação",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaEntity);

		final var lancamentoResultEntity = LancamentoComponent.createSLancamentoEntity(1L, "Despesas com cursos",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaEntity);

		when(lancamentoRepository.findById(lancamentoDto.getId())).thenReturn(Optional.of(lancamentoEntity));
		when(lancamentoRepository.save(lancamentoResultEntity)).thenReturn(lancamentoResultEntity);
		when(lancamentoMapper.toDto(lancamentoResultEntity)).thenReturn(lancamentoResultDto);

		final var result = this.atualizaLancamentoService.atualizar(lancamentoDto, 1L);
		assertThat(result.getId(), is(lancamentoDto.getId()));
		assertThat(result.getComentario(), is(lancamentoDto.getComentario()));
	}

	@Test
	public void tenta_atualiza_uma_lancamento_que_nao_existe() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = LancamentoComponent.createLancamentoDto(1L, "Despesas com cursos",
				BigDecimal.valueOf(-100), LocalDate.now(), subcategoriaDto);

		doThrow(LancamentoNotFoundExcpetion.class).when(lancamentoRepository).findById(2L);

		Assertions.assertThrows(LancamentoNotFoundExcpetion.class,
				() -> this.atualizaLancamentoService.atualizar(lancamentoDto, 2L));
	}

	@Test
	public void lancamento_nao_encontrado() {
		final var idLancamento = 1L;
		doThrow(LancamentoNotFoundExcpetion.class).when(lancamentoRepository).findById(idLancamento);
		Assertions.assertThrows(LancamentoNotFoundExcpetion.class,
				() -> this.consultaLancamentoService.retornaLancamentoPorId(idLancamento));

	}


}
