package com.developement.crm.services;

import com.auth0.jwt.JWT;
import com.developement.crm.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public static String getUserbySession(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
                return userDetails.getUsername();
            } else {
                // Trate o caso em que não há usuário autenticado.
                return "Nenhum usuário autenticado encontrado";
            }
    }

    public static String getUserbyToken(String token) {
        return JWT.decode(token).getSubject();

    }
}
