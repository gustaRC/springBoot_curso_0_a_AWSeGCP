package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringBootCurso0AAwSeGcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCurso0AAwSeGcpApplication.class, args);

		// COMO FUNCIONARÁ NA PRÁTICA O ENCODE DE SENHA ATRAVES DO @Bean DO SecurityConfig
		generateHashedPassword();
	}

	private static void generateHashedPassword() {
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

		System.out.println("Senha 'admin123': " + passwordEncoder.encode("admin123"));
		System.out.println("Senha 'biricubicu': " + passwordEncoder.encode("biricubicu"));
	}
}
