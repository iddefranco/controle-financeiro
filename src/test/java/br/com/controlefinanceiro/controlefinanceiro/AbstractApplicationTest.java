package br.com.controlefinanceiro.controlefinanceiro;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AbstractApplicationTest {

    protected static final String ENDPOINT_CATEGORIAS = "categorias";
    protected static final String ENDPOINT_SUBCATEGORIAS = "subcategorias";
    protected static final String ENDPOINT_LANCAMENTOS = "lancamentos";

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected MockMvc mockMvc;

    protected ResultActions result;

    @PostConstruct
    private void postConstruct() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @SneakyThrows
    public void perform(final MockHttpServletRequestBuilder request) {
        this.result = this.mockMvc.perform(request);
    }

}