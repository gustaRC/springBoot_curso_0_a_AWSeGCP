package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1.security;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCredentialsDTO {
// DTO que será enviado pelo usuário -> iremos processar os dados -> retornar uma resposta (outro DTO)

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
}
