package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 1. tentar obter o token
        String token = tokenProvider.resolveToken((HttpServletRequest) request); // (cast) request
        if (StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)) {
            //gera o Authentication
            Authentication authentication = tokenProvider.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication); // sucesso -> Usuário autenticado!
            }
        }

        chain.doFilter(request, response); // prossegue o fluxo de vida do Spring (proxima etapa do fluxo)
    }
}
