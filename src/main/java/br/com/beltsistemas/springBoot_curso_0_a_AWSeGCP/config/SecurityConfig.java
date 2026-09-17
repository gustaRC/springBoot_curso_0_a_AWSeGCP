package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt.JwtTokenFilter;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
// soma das duas Annotations = classe de configuração de autenticação
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    // bean para validar a senha
    @Bean // Anotação usada no Spring para explicar um objeto retornado que deve ser gerenciado pelo Spring (Container IOC)
    PasswordEncoder passwordEncoder() {
        String encodeKey = "pbkdf2";
        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8,
                185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        /* Parâmetros do Encoder:
            1. Vazio == gerado automaticamente
            2. comprimento == 8 bytes
            3. quantidade de vezes que o algoritmo será aplicado == 185000
            4. algoritmo de hash que será utilizado == Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        */

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodeKey, pbkdf2Encoder);

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(encodeKey, encoders);
        // 1. nome do algoritmo que será utilizado | 2. encoder que gerará o token

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider); // segurança que confere o Crachá (token JWT) antes da pessoa chegar na entrada principal

        //@formatter:off
        return http
                .httpBasic(AbstractHttpConfigurer::disable) // desliga autenticação HTTP Basic (popup na janela do navegador)
                .csrf(AbstractHttpConfigurer::disable) // protege contra ataques de sites maliciosos, obrigando a usar os cookies da sessão logada
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class) // o segurança (customFilter) confere o crachá antes do filtro padrão do Spring
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        // não guarda registro de quem já entrou, cada requisição precisa se identificar
                )
                .authorizeHttpRequests(
                        authorize -> authorize.requestMatchers(
                                "/auth/signin",
                                "/auth/refresh/**", // auth/refresh/[QUALQUER-COISA-QUE-VIER-DEPOIS]
                                "/auth/createUser", // endpoint não pode ficar disponível em ambiente PRODUÇÃO
                                "/swagger-ui/**",
                                "v3/api-docs/**"
                        ).permitAll() // permitir acesso deliberadamente
                                .requestMatchers("/api/**").authenticated() // somente permissão SE autenticado
                                .requestMatchers("/user").denyAll() // Adicionado JPA, pode-se expor as entidades como Endpoints, nisso precisamos bloquear independente de estar autenticado ou não
                )
                .cors(cors -> {}) // Já temos um configurador de Cors, com isso aqui pode ficar vazio
                .build(); // Monta e retorna a cadeia de filtro, o manual de regras para o Spring aplicar a cada requisição

        //@formatter:on
    }

}
