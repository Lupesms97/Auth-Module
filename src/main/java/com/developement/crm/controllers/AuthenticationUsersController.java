package com.developement.crm.controllers;

import com.developement.crm.dtos.*;
import com.developement.crm.model.UserModel;
import com.developement.crm.repositories.UsersRepository;
import com.developement.crm.services.TokenService;
import com.developement.crm.services.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationUsersController {


    private final UsersService usersService;


    private final TokenService tokenService;


    private final AuthenticationManager authenticationManager;


    private final UsersRepository usersRepository;



    @GetMapping("/make")
    public String makeChanges(){

        try{

            List<UserModel> users = usersRepository.findAll();

            for(UserModel user : users){
                String oldPassword = user.getPassword();
                String newPassword = new BCryptPasswordEncoder().encode(oldPassword);

                String token = tokenService.generateToken(user);


                user.setPassword(newPassword);

                usersRepository.save(user);
            }
            return "feito com sucesso";

        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto data){

        try {
            UserModel user = usersService.findUserByLogin(data.getLogin());

            if (user == null) {
                String message = "Usuário não encontrado"+data.getLogin();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
            }

            var userNamePassword = new UsernamePasswordAuthenticationToken(data.getLogin(), data.getPassword());
            var authentication = authenticationManager.authenticate(userNamePassword);

            var token = tokenService.generateToken((UserModel) authentication.getPrincipal());
            String message = "Usuário logado com sucesso";
            return ResponseEntity.ok().body(new ResponseLoginDto(token, message));

        } catch (BadCredentialsException e) {
            String message = "Dados de usuário inválidos";
            var token = "";
            return ResponseEntity.badRequest().body(new ResponseLoginDto(token, message));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersDto user){
        HashMap mensagem = new HashMap();

        try {
            UserDetails userDetails = usersRepository.findByLogin(user.getLogin());
            if (userDetails != null) {
                mensagem.put("mensagem", "login já castrato");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(mensagem);
            }else {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                usersService.creatNewUser(UsersDto.convertToUserModel(user));

                mensagem.put("mensagem", "usuario cadastrado com sucesso");

                return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }
    @PostMapping("validation")
    public TokenValidation validation(@RequestParam String tokenKey){
        String response = tokenService.validateToken(tokenKey);
        return (response!= "") ? new TokenValidation(tokenKey, "valid") : new TokenValidation(tokenKey, "invalid");
    }
}