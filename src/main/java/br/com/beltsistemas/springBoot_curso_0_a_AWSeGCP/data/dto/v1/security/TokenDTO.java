package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.security;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TokenDTO {
// DTO que será enviado pelo usuário -> iremos processar os dados -> retornar uma resposta (outro DTO)

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;
}
