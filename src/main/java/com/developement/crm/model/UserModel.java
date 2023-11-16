package com.developement.crm.model;

import com.developement.crm.enums.Roles;
import com.developement.crm.enums.Unidades;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name= "users")
@Table(name="users")
@EqualsAndHashCode(of="id")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String login;
    private String password;
    @Column(unique = true)
    private String email;
    private String name;
    private String token;
    @Enumerated(EnumType.STRING)
    private Unidades unidade;
    private LocalDateTime creationDate;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime acesso;
    @Enumerated(EnumType.STRING)
    private Roles role;



    public UserModel(String login, String password, String name, Unidades unidade) {
        this.creationDate = LocalDateTime.now();
        this.login = login;
        this.password = password;
        this.name = name;
        this.unidade = unidade;
    }
    public UserModel(String login, String password, Roles role){
        this.login = login;
        this.password = password;
        this.role = role;
    }
    public String getRoleString(){
        return this.role.toString();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (this.role == Roles.ADMIN)?
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_USER")):
                List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
