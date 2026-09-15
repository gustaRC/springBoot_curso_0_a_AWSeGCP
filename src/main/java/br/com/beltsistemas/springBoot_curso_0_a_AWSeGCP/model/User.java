package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {
/* Está classe não pode ser tratada como uma Entidade convencional,
   pois ela implementa a interface UserDetails do Spring Security,
   que é uma interface que representa um usuário do sistema. */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true) // campo unico, ou seja, não pode repetir
    private String userName;

    @Column(name = "full_name")
    private String fullName;

    @Column()
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column()
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER) // ou seja, quando carregar User, irá trazer também as Permissions
    @JoinTable(
            name = "user_permission",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permission")}
    )
    private List<Permission> permissions; // N:N

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();

        for (Permission permission : permissions) {
            roles.add(permission.getDescription());
        }

        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
