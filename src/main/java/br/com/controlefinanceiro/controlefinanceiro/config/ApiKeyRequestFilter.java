package br.com.controlefinanceiro.controlefinanceiro.config;

import br.com.controlefinanceiro.controlefinanceiro.apikey.entity.ApiKey;
import br.com.controlefinanceiro.controlefinanceiro.apikey.repository.ApiKeyRepository;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
@Profile("!test")
public class ApiKeyRequestFilter extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(ApiKeyRequestFilter.class);

    private ApiKeyRepository apiKeyRepository;

    public ApiKeyRequestFilter(ApiKeyRepository apiKeyRepository){
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();

        if(path.contains("swagger") ||
                path.contains("/v2/api-docs") ||
                path.contains("actuator") ){
            chain.doFilter(request, response);
            return;
        }
        String key = req.getHeader("api-key") == null ? "" : req.getHeader("api-key");
        LOG.info("Trying key: " + key);

        Optional<ApiKey> apiKeyOptional = this.apiKeyRepository.findOneByKey(key);
        if(apiKeyOptional.isPresent()){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse resp = (HttpServletResponse) response;
            String error = "Invalid API KEY";

            resp.reset();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentLength(error .length());
            response.getWriter().write(error);
        }

    }
}