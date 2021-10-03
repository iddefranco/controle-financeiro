package br.com.controlefinanceiro.controlefinanceiro.categoria.integration;

import br.com.controlefinanceiro.controlefinanceiro.AbstractApplicationTest;
import br.com.controlefinanceiro.controlefinanceiro.categoria.component.CategoriaComponent;
import br.com.controlefinanceiro.controlefinanceiro.categoria.dto.CategoriaDto;
import br.com.controlefinanceiro.controlefinanceiro.categoria.entity.CategoriaEntity;
import br.com.controlefinanceiro.controlefinanceiro.categoria.mapper.CategoriaMapper;
import br.com.controlefinanceiro.controlefinanceiro.categoria.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
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
public class CategoriaServiceIntegration extends AbstractApplicationTest {
	private final CategoriaRepository categoriaRepository;

	private final CategoriaMapper categoriaMapper;

	@Test
	void salva_uma_categoria_com_sucesso() throws Exception {
		final var categoriaDto = mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Educação"));

		final var content = new JSONObject()
				.put("nome", categoriaDto.getNome())
				.toString();

		mockMvc.perform(post("/v1/" + ENDPOINT_CATEGORIAS)
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.nome").exists())
				.andExpect(jsonPath("$.nome").value("Despesas com Educação"));
	}

	@Test
	void atualiza_uma_categoria_com_sucesso() throws Exception {
		final var categoriaDto = mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Educação"));
		final var categoriaEdicao = CategoriaComponent.createCategoriaDto(null, "Despesas com Casa");

		final var content = new JSONObject()
				.put("idCategoria", categoriaEdicao.getId())
				.put("nome", categoriaEdicao.getNome())
				.toString();

		mockMvc.perform(put("/v1/" + ENDPOINT_CATEGORIAS + "/" + categoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(content))
				.andDo(print())
				.andExpect(status().isNoContent())
				.andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	void retorna_categoria_por_id() throws Exception {
		final var categoriaDto =  mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Educação"));

		mockMvc.perform(get("/v1/" + ENDPOINT_CATEGORIAS + "/" + categoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.id").value(categoriaDto.getId()))
				.andExpect(jsonPath("$.nome").exists())
				.andExpect(jsonPath("$.nome").value(categoriaDto.getNome()));
	}

	@Test
	void deleta_categoria_por_id() throws Exception {
		final var categoriaDto =  mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Educação"));

		mockMvc.perform(delete("/v1/" + ENDPOINT_CATEGORIAS + "/" + categoriaDto.getId())
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@Test
	void deleta_categoria_por_id_nao_encontrada() throws Exception {
		final var idCategoria = 100;
		mockMvc.perform(delete("/v1/" + ENDPOINT_CATEGORIAS + "/" + idCategoria)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("002"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Categoria não encontrada"));
	}

	@Test
	void categoria_por_id_nao_encontrada() throws Exception {
		final var categoriaId = 100;

		mockMvc.perform(get("/v1/" + ENDPOINT_CATEGORIAS + "/" + categoriaId)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.codigo").exists())
				.andExpect(jsonPath("$.codigo").value("002"))
				.andExpect(jsonPath("$.mensagem").exists())
				.andExpect(jsonPath("$.mensagem").value("Categoria não encontrada"));
	}

	@Test
	void retorna_todas_categoria() throws Exception {
		final var categoriaDespesaEducacao =  mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Educação"));
		final var categoriaDespesaCasa =  mockCategoriaSave(CategoriaComponent.createCategoriaDto(null, "Despesas com Casa"));

		mockMvc.perform(get("/v1/" + ENDPOINT_CATEGORIAS)
						.param("page", "0")
						.param("size", "20")
						.param("sort", "id")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").exists())
				.andExpect(jsonPath("$.content[0].nome").exists())
				.andExpect(jsonPath("$.content[1].id").exists())
				.andExpect(jsonPath("$.content[1].nome").exists());
	}

	private CategoriaDto mockCategoriaSave(final CategoriaDto categoriaDto) {
		CategoriaEntity categoriaEntity = categoriaRepository.save(categoriaMapper.toEntity(categoriaDto));
		return categoriaMapper.toDto(categoriaEntity);
	}
}
