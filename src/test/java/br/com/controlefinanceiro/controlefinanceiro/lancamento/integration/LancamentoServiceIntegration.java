package br.com.controlefinanceiro.controlefinanceiro.lancamento.integration;

import br.com.controlefinanceiro.controlefinanceiro.AbstractApplicationTest;
import br.com.controlefinanceiro.controlefinanceiro.categoria.component.CategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.component.LancamentoComponent;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.dto.LancamentoDto;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.entity.LancamentoEntity;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.mapper.LancamentoMapper;
import br.com.controlefinanceiro.controlefinanceiro.lancamento.repository.LancamentoRepository;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.component.SubcategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LancamentoServiceIntegration extends AbstractApplicationTest {
	private final SubcategoriaRepository subcategoriaRepository;

	private final SubcategoriaMapper subcategoriaMapper;

	private final LancamentoRepository lancamentoRepository;

	private final LancamentoMapper lancamentoMapper;

	private final CategoriaRepository categoriaRepository;

	private final CategoriaMapper categoriaMapper;

	@BeforeEach
	private void setup() {
		this.lancamentoRepository.deleteAll();
		this.subcategoriaRepository.deleteAll();
		this.categoriaRepository.deleteAll();
	}

	@Test
	void salva_uma_lancamento_com_sucesso() throws Exception {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = mockLancamentoSave(
				LancamentoComponent.createLancamentoDto(null,
						"Despesas com Educação",
						BigDecimal.valueOf(-100.0), LocalDate.now(),
						subcategoriaDto));

		final var content = this.createObjectMapper()
				.writeValueAsString(lancamentoDto);

		mockMvc.perform(post("/v1/" + ENDPOINT_LANCAMENTOS)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.comentario").exists())
				.andExpect(jsonPath("$.comentario").value(lancamentoDto.getComentario()))
				.andExpect(jsonPath("$.valor").exists())
				.andExpect(jsonPath("$.valor").value(lancamentoDto.getValor()))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").value(lancamentoDto.getData().toString()))
				.andExpect(jsonPath("$.subcategoria").exists());
	}

	@Test
	void atualiza_um_lancamento_com_sucesso() throws Exception {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = mockLancamentoSave(
				LancamentoComponent.createLancamentoDto(null,
						"Despesas com Educação",
						BigDecimal.valueOf(-100), LocalDate.now(),
						subcategoriaDto));


		final var content = this.createObjectMapper().writeValueAsString(lancamentoDto);

		mockMvc.perform(put("/v1/" + ENDPOINT_LANCAMENTOS + "/" + lancamentoDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isNoContent());
	}


	@Test
	void retorna_lancamento_por_id() throws Exception {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = mockLancamentoSave(
				LancamentoComponent.createLancamentoDto(null,
						"Despesas com Educação",
						BigDecimal.valueOf(-100), LocalDate.now(),
						subcategoriaDto));

		mockMvc.perform(get("/v1/" + ENDPOINT_LANCAMENTOS + "/" + lancamentoDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.comentario").exists())
				.andExpect(jsonPath("$.comentario").value(lancamentoDto.getComentario()))
				.andExpect(jsonPath("$.valor").exists())
				.andExpect(jsonPath("$.valor").value("-100.0"))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").value(lancamentoDto.getData().toString()))
				.andExpect(jsonPath("$.subcategoria").exists());
	}

	@Test
	void deleta_lancamento_por_id() throws Exception {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = mockLancamentoSave(
				LancamentoComponent.createLancamentoDto(null,
						"Despesas com Educação",
						BigDecimal.valueOf(-100), LocalDate.now(),
						subcategoriaDto));

		mockMvc.perform(delete("/v1/" + ENDPOINT_LANCAMENTOS + "/" + lancamentoDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void deleta_lancamento_por_id_nao_encontrada() throws Exception {
		final var idCategoria = 100;
		mockMvc.perform(delete("/v1/" + ENDPOINT_LANCAMENTOS + "/" + idCategoria)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("009"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Lançamento não encontrado"));
	}

	@Test
	void lancamento_por_id_nao_encontrado() throws Exception {
		final var categoriaId = 100;

		mockMvc.perform(get("/v1/" + ENDPOINT_LANCAMENTOS + "/" + categoriaId)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("009"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Lançamento não encontrado"));
	}

	/*@Test
	void retorna_todos_lancamento() throws Exception {
		final var categoriaDto = CategoriaComponent.createCategoriaDto(1L, "Despesa Fixa");
		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(1L, "Despesas com Educação", categoriaDto);

		final var lancamentoDto = mockLancamentoSave(
				LancamentoComponent.createLancamentoDto(null,
						"Despesas com Educação",
						BigDecimal.valueOf(-100), LocalDate.now(),
						subcategoriaDto));
		System.out.println(lancamentoDto);
		mockMvc.perform(get("/v1/" + ENDPOINT_LANCAMENTOS)
						.param("dataInicio", "2020-10-01")
						.param("dataFim", "2020-10-01")
						.param("page", "0")
						.param("size", "20")
						.param("sort", "id")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").exists())
				.andExpect(jsonPath("$.content[0].comentario").exists())
				.andExpect(jsonPath("$.content[0].data").exists())
				.andExpect(jsonPath("$.content[0].subcategoria").exists())
				.andExpect(jsonPath("$.content[0].valor").exists());
	}*/

	private LancamentoDto mockLancamentoSave(final LancamentoDto lancamentoDto) {
		CategoriaEntity categoriaEntity = categoriaRepository.save(categoriaMapper.toEntity(lancamentoDto.getSubcategoria().getCategoria()));
		SubcategoriaEntity subcategoriaSalva = subcategoriaMapper.toEntity(lancamentoDto.getSubcategoria());

		subcategoriaSalva.setCategoria(categoriaEntity);
		SubcategoriaEntity subcategoriaResultEntity = subcategoriaRepository.save(subcategoriaSalva);

		lancamentoDto.setSubcategoria(subcategoriaMapper.toDto(subcategoriaResultEntity));
		LancamentoEntity lancamentoEntity = lancamentoMapper.toEntity(lancamentoDto);
		LancamentoEntity lancamentoSalvo = lancamentoRepository.save(lancamentoEntity);


		return lancamentoMapper.toDto(lancamentoSalvo);
	}

	private ObjectMapper createObjectMapper() {
		return new ObjectMapper()
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.registerModule(new JSR310Module());
	}

}
