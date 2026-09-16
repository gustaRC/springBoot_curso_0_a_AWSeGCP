package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.config;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

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

}
