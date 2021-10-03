package br.com.controlefinanceiro.controlefinanceiro.subcategoria.unit;


import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.LancamentoNotSaveException;
import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaInvalidaExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.SubcategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.component.SubcategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.AtualizaSubcategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.ConsultaSubcategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.service.PersisteSubcategoriaService;
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
public class SubcategoriaServiceTest {
	@InjectMocks
	private PersisteSubcategoriaService persisteSubcategoriaService;

	@InjectMocks
	private AtualizaSubcategoriaService atualizaSubcategoriaService;

	@InjectMocks
	private ConsultaSubcategoriaService consultaSubcategoriaService;

	@Mock
	private SubcategoriaRepository repository;

	@Mock
	private SubcategoriaMapper subcategoriaMapper;

	@Test
	public void salva_subcategoria_com_sucesso() {
		final var categoriaDto = CategoriaDto.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();

		final var categoriaEntity = CategoriaEntity.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(null, "Casa", categoriaDto);

		final var categoriaResultDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Casa", categoriaDto);
		final var subcategoriaSalva = SubcategoriaComponent.createSubcategoriaEntity(1L, "Casa", categoriaEntity);

		when(subcategoriaMapper.toEntity(subcategoriaDto)).thenReturn(subcategoriaSalva);
		when(repository.save(subcategoriaSalva)).thenReturn(subcategoriaSalva);
		when(subcategoriaMapper.toDto(subcategoriaSalva)).thenReturn(categoriaResultDto);

		final var result = this.persisteSubcategoriaService.salvar(subcategoriaDto);
		assertThat(result, is(not(nullValue())));
		assertThat(result.getId(), is(not(nullValue())));
	}

	@Test
	public void tenta_salvar_uma_subcategoria_invalida() {
		SubcategoriaDto subcategoriaDto = null;
		Assertions.assertThrows(SubcategoriaInvalidaExcpetion.class, () -> this.persisteSubcategoriaService.salvar(subcategoriaDto));

	}

	@Test
	public void tenta_salvar_uma_subcategoria_sem_nome() {
		final var categoriaDto = CategoriaDto.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(null, null, categoriaDto);
		Assertions.assertThrows(LancamentoNotSaveException.class, () -> this.persisteSubcategoriaService.salvar(subcategoriaDto));
	}

	@Test
	public void atualiza_uma_subcategoria() {
		final var categoriaDto = CategoriaDto.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();


		final var categoriaEntity = CategoriaEntity.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesa com Aluguel", categoriaDto);
		final var categoriaResultDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesa com Aluguel", categoriaDto);
		final var subcategoriaSalva = SubcategoriaComponent.createSubcategoriaEntity(1L, "Despesa com Casa", categoriaEntity);
		final var subcategoriaAtualizada = SubcategoriaComponent.createSubcategoriaEntity(1L, "Despesa com Aluguel", categoriaEntity);

		when(repository.findById(subcategoriaDto.getId())).thenReturn(Optional.of(subcategoriaSalva));
		when(repository.save(subcategoriaAtualizada)).thenReturn(subcategoriaAtualizada);
		when(subcategoriaMapper.toDto(subcategoriaAtualizada)).thenReturn(categoriaResultDto);

		this.atualizaSubcategoriaService.atualizar(subcategoriaDto, 1L);
		assertThat(subcategoriaAtualizada.getId(), is(categoriaResultDto.getId()));
		assertThat(subcategoriaAtualizada.getNome(), is(categoriaResultDto.getNome()));
	}

	@Test
	public void tenta_atualiza_uma_categoria_que_nao_existe() {
		final var categoriaDto = CategoriaDto.builder()
				.id(1L)
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(2L, "Despesa com Agua", categoriaDto);
		final SubcategoriaNotFoundExcpetion subcategoriaNotFoundExcpetion = new SubcategoriaNotFoundExcpetion();

		doThrow(SubcategoriaNotFoundExcpetion.class).when(repository).findById(2L);

		Assertions.assertThrows(SubcategoriaNotFoundExcpetion.class,
				() -> this.atualizaSubcategoriaService.atualizar(subcategoriaDto, 2L));

	}

	@Test
	public void categoria_nao_encontrada() {
		final var idSubcategoria = 1L;
		doThrow(CategoriaNotFoundExcpetion.class).when(repository).findById(idSubcategoria);
		Assertions.assertThrows(CategoriaNotFoundExcpetion.class,
				() -> this.consultaSubcategoriaService.retornaCategoriaPorId(idSubcategoria));

	}


}
