package com.developement.crm.services;

import com.auth0.jwt.JWT;
import com.developement.crm.exceptionHandlers.NoUserFindOnSession;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    UsersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public static String getUserbySession(){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return userDetails.getUsername();
            } else {
                // Trate o caso em que não há usuário autenticado.
                return "Nenhum usuário autenticado encontrado";
            }

    }

    public static String getUserbyToken(String token) {
        String user = JWT.decode(token).getSubject();
        return user;
    }
}
