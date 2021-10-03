package br.com.controlefinanceiro.controlefinanceiro.categoria.unit;


import br.com.controlefinanceiro.controlefinanceiro.categoria.component.CategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.PersisteCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.RemoveCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.ConsultaCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.categoria.service.AtualizaCategoriaService;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaInvalidaExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotFoundExcpetion;
import br.com.controlefinanceiro.controlefinanceiro.exception.CategoriaNotSaveException;
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
public class CategoriaServiceTest {
	@InjectMocks
	private PersisteCategoriaService salvaCategoriaService;

	@InjectMocks
	private AtualizaCategoriaService atualizaCategoriaService;

	@InjectMocks
	private ConsultaCategoriaService consultaCategoriaService;

	@InjectMocks
	private RemoveCategoriaService removeCategoriaService;

	@Mock
	private CategoriaRepository repository;

	@Mock
	private CategoriaMapper categoriaMapper;

	@Test
	public void salva_categoria_com_sucesso() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(null, "Casa");
		final var categoriaResultDto = CategoriaComponent.createCategoriaDto(1L, "Casa");
		final var categoriaSalva = CategoriaComponent.createCategoriaEntity(1L, "Casa");

		when(repository.save(categoriaSalva)).thenReturn(categoriaSalva);
		when(categoriaMapper.toEntity(categoriaDto)).thenReturn(categoriaSalva);
		when(categoriaMapper.toDto(categoriaSalva)).thenReturn(categoriaResultDto);

		final var result = this.salvaCategoriaService.salvar(categoriaDto);
		assertThat(result, is(not(nullValue())));
		assertThat(result.getId(), is(not(nullValue())));
	}

	@Test
	public void tenta_salvar_uma_categoria_invalida() {
		CategoriaDto categoriaDto = null;
		Assertions.assertThrows(CategoriaInvalidaExcpetion.class, () -> this.salvaCategoriaService.salvar(categoriaDto));

	}

	@Test
	public void tenta_salvar_uma_categoria_sem_nome() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(null, null);
		Assertions.assertThrows(CategoriaNotSaveException.class, () -> this.salvaCategoriaService.salvar(categoriaDto));

	}
	@Test
	public void atualiza_uma_categoria() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa com Casa");
		final var categoriaResultDto = CategoriaComponent.createCategoriaDto(1L, "Despesa com Casa");
		final var categoriaSalva = CategoriaComponent.createCategoriaEntity(1L, "Casa");
		final var categoriaAtualizada = CategoriaComponent.createCategoriaEntity(1L, "Despesa com Casa");

		when(repository.findById(categoriaDto.getId())).thenReturn(Optional.of(categoriaSalva));
		when(repository.save(categoriaAtualizada)).thenReturn(categoriaAtualizada);
		when(categoriaMapper.toDto(categoriaAtualizada)).thenReturn(categoriaResultDto);

		this.atualizaCategoriaService.atualizar(categoriaDto, 1L);
		assertThat(categoriaAtualizada.getId(), is(categoriaResultDto.getId()));
		assertThat(categoriaAtualizada.getNome(), is(categoriaResultDto.getNome()));
	}

	@Test
	public void tenta_atualiza_uma_categoria_que_nao_existe() {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(2L, "Despesa com Casa");
		doThrow(CategoriaNotFoundExcpetion.class).when(repository).findById(2L);
		Assertions.assertThrows(CategoriaNotFoundExcpetion.class,
				() -> this.atualizaCategoriaService.atualizar(categoriaDto, 2L));

	}

	@Test
	public void categoria_nao_encontrada() {
		final var idCategoria = 1L;
		doThrow(CategoriaNotFoundExcpetion.class).when(repository).findById(idCategoria);
		Assertions.assertThrows(CategoriaNotFoundExcpetion.class,
				() -> this.consultaCategoriaService.retornaCategoriaPorId(idCategoria));

	}


}
