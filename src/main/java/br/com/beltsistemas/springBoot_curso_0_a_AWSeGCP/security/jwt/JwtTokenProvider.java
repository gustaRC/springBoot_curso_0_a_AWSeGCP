package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.security.TokenDTO;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-lenght:secret}")
    private long validityInMilliseconds = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct // executar uma ação logo após a inicialização do Spring Context e antes do Usuário realizar qualquer ação (sem a Annotation isso não pode acontecer)
    protected void init() {
        // setar secret e algorithm
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createAccessToken(String username, List<String> roles) {

        Date now =  new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String accessToken = getAccessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);

        return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        //todo
        return "";
    }

    private String getRefreshToken(String username, List<String> roles, Date now) {
        //todo
        return "";
    }
}
