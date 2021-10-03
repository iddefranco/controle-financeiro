package br.com.controlefinanceiro.controlefinanceiro.subcategoria.integration;

import br.com.controlefinanceiro.controlefinanceiro.AbstractApplicationTest;
import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.component.SubcategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.dto.SubcategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.entity.SubcategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.mapper.SubcategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.subcategoria.repository.SubcategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubcategoriaServiceIntegration extends AbstractApplicationTest {
	private final CategoriaRepository categoriaRepository;

	private final SubcategoriaRepository subcategoriaRepository;

	private final CategoriaMapper categoriaMapper;

	private final SubcategoriaMapper subcategoriaMapper;

	@Test
	void salva_uma_subcategoria_com_sucesso() throws Exception {
		final var categoriaDto = CategoriaDto.builder()
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaSalvo = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", null), categoriaDto);

		final var subcategoriaDto = SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", subcategoriaSalvo.getCategoria());

		final var content = new ObjectMapper().writeValueAsString(subcategoriaDto);

		mockMvc.perform(post("/v1/" + ENDPOINT_SUBCATEGORIAS)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.nome").exists())
				.andExpect(jsonPath("$.nome").value("Despesas com Educação"))
				.andExpect(jsonPath("$.categoria").exists());
	}

	@Test
	void atualiza_uma_categoria_com_sucesso() throws Exception {
		final var categoriaDto = CategoriaDto.builder()
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", null), categoriaDto);

		final var subcategoriaEdicao = SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Faculade", subcategoriaDto.getCategoria());

		final var content = new ObjectMapper().writeValueAsString(subcategoriaEdicao);

		mockMvc.perform(put("/v1/" + ENDPOINT_SUBCATEGORIAS + "/" + subcategoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void retorna_subcategoria_por_id() throws Exception {
		final var categoriaDto = CategoriaDto.builder()
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", null), categoriaDto);

		mockMvc.perform(get("/v1/" + ENDPOINT_SUBCATEGORIAS + "/" + subcategoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(subcategoriaDto.getId()))
				.andExpect(jsonPath("$.nome").exists())
				.andExpect(jsonPath("$.nome").value(subcategoriaDto.getNome()));
	}

	@Test
	void deleta_subcategoria_por_id() throws Exception {
		final var categoriaDto = CategoriaDto.builder()
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDto = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", null), categoriaDto);

		mockMvc.perform(delete("/v1/" + ENDPOINT_SUBCATEGORIAS + "/" + subcategoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void deleta_subcategoria_por_id_nao_encontrada() throws Exception {
		final var idCategoria = 100;
		mockMvc.perform(delete("/v1/" + ENDPOINT_SUBCATEGORIAS + "/" + idCategoria)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("006"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Subcategoria não encontrada"));
	}

	@Test
	void subcategoria_por_id_nao_encontrada() throws Exception {
		final var categoriaId = 100;

		mockMvc.perform(get("/v1/" + ENDPOINT_SUBCATEGORIAS + "/" + categoriaId)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("006"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Subcategoria não encontrada"));
	}

	@Test
	void retorna_todas_subcategoria() throws Exception {
		final var categoriaDto = CategoriaDto.builder()
				.nome("Despesa com Casa")
				.build();

		final var subcategoriaDespesaEducacao = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Educação", null), categoriaDto);

		final var subcategoriaDespesaCasa = mockSubcategoriaSave(
				SubcategoriaComponent.createSubcategoriaDto(null, "Despesas com Casa", null), categoriaDto);

		mockMvc.perform(get("/v1/" + ENDPOINT_SUBCATEGORIAS)
						.param("page", "0")
						.param("size", "20")
						.param("sort", "id")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").exists())
				.andExpect(jsonPath("$.content[0].nome").exists())
				.andExpect(jsonPath("$.content[0].categoria").exists());
	}

	private SubcategoriaDto mockSubcategoriaSave(final SubcategoriaDto subcategoriaDto, final CategoriaDto categoriaDto) {
		CategoriaEntity categoriaEntity = categoriaRepository.save(categoriaMapper.toEntity(categoriaDto));
		SubcategoriaEntity subcategoriaSalva = subcategoriaMapper.toEntity(subcategoriaDto);

		subcategoriaSalva.setCategoria(categoriaEntity);
		SubcategoriaEntity subcategoriaResultEntity = subcategoriaRepository.save(subcategoriaSalva);

		return subcategoriaMapper.toDto(subcategoriaResultEntity);
	}

}
