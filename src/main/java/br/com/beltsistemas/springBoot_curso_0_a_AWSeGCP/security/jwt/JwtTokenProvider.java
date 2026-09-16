package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.security.jwt;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.security.TokenDTO;
import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.exception.InvalidJwtAuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {
/*  Pra fixar a lógica geral:
        pensa no JWT como uma pulseira de festival. Na entrada (login),
        a produção (seu createAccessToken) carimba a pulseira com validade
        e informações do portador (roles), e assina com um selo
        secreto (algorithm/secretKey) que só a produção conhece.
        Toda vez que você quer entrar em uma área (uma requisição),
        o segurança (decodedToken + validateToken) confere o selo
        e a validade — sem precisar te perguntar de novo quem você é.
*/

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-lenght:secret}")
    private long validityInMilliseconds = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    // OBJETIVO: Inicializa a secret key (codificada em Base64) e o algoritmo de assinatura (HMAC256) assim que o bean é criado.
    @PostConstruct // executar uma ação logo após a inicialização do Spring Context e antes do Usuário realizar qualquer ação (sem a Annotation isso não pode acontecer)
    protected void init() {
        // setar secret e algorithm
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    // OBJETIVO: Orquestra a geração do access token e do refresh token para o usuário, retornando ambos dentro de um TokenDTO.
    public TokenDTO createAccessToken(String username, List<String> roles) {

        Date now =  new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String accessToken = getAccessToken(username, roles, now, validity);
        String refreshToken = getRefreshToken(username, roles, now);

        return new TokenDTO(username, true, now, validity, accessToken, refreshToken);
    }

    // OBJETIVO: Monta e assina o JWT de acesso, incluindo roles, issuer, data de emissão e data de expiração.
    private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        // responsável por: pegando a URL do site (ex.: http://localhost:8080)

        return JWT.create()
                .withClaim("roles", roles) //= roles
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issuerUrl)
                .sign(algorithm);
    }

    // OBJETIVO: Monta e assina o JWT de refresh (validade própria, sem issuer), usado para obter um novo access token sem exigir login novamente.
    private String getRefreshToken(String username, List<String> roles, Date now) {
        Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds * 3));

        return JWT.create()
                .withClaim("roles", roles) //= roles
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .withSubject(username)
                .sign(algorithm);
    }

    // OBJETIVO: Descodifica o token, carrega o UserDetails correspondente ao subject e retorna o objeto Authentication do Spring Security.
    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject()); // obtém a autenticação e retorna se está autenticado ou não

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // OBJETIVO: Verifica a assinatura do token com a secret key e retorna o JWT já descodificado.
    private DecodedJWT decodedToken(String token) {
        // verificando com a assinatura (secret-key) se o token é valido
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    // OBJETIVO: Extrai o token do header "Authorization", removendo o prefixo "Bearer ".
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // Bearer [TOKEN]
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else {
            throw new InvalidJwtAuthenticationException("Invalid JWT Token");
        }
    }

    // OBJETIVO: Confere se o token continua dentro do prazo de validade (não expirado).
    public boolean validateToken(String token) {
        DecodedJWT decodedJWT = decodedToken(token);

        try {
            if (decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or Invalid JWT Token");
        }
    }
}
