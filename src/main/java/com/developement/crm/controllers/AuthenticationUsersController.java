package com.developement.crm.controllers;

import com.developement.crm.dtos.*;
import com.developement.crm.dtos.UserLoginDto;
import com.developement.crm.dtos.UsersDto;
import com.developement.crm.enums.StatusToken;
import com.developement.crm.exceptionHandlers.UserNotFoundException;
import com.developement.crm.model.UserModel;
import com.developement.crm.rabbit.service.RabbitMqService;
import com.developement.crm.repositories.UsersRepository;
import com.developement.crm.services.TokenService;
import com.developement.crm.services.UsersService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import constants.RabbitMqConstants;
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationUsersController {


    private final UsersService usersService;

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final UsersRepository usersRepository;

    private final RabbitMqService rabbitMqService;



    @GetMapping("/make")
    public String makeChanges(){

        try{

            List<UserModel> users = usersRepository.findAll();

            for(UserModel user : users){
                String oldPassword = user.getPassword();
                String newPassword = new BCryptPasswordEncoder().encode(oldPassword);

//                String token = tokenService.generateToken(user);

                user.setPassword(newPassword);

                usersRepository.save(user);
            }
            return "feito com sucesso";

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseLoginDto.class)) }),
            @ApiResponse(responseCode = "401", description = "User or password not found", content =
            { @Content(mediaType = "application/json", schema =
            @Schema(implementation = UserNotFoundException.class)) }) })

@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto data){

        try {
            rabbitMqService.send(RabbitMqConstants.FILA_LOGIN, data);

            UserModel user = usersService.findUserByLogin(data.getLogin());

            if (user == null) {
                String message = "Usuário não encontrado"+data.getLogin();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "usuario cadastrado com sucesso", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)) }),
            @ApiResponse(responseCode = "409", description = "login ou email já castrato", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = MessageDto.class)) }) })

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersDto user){


        try {

            rabbitMqService.send(RabbitMqConstants.FILA_CREATION,user);

            UserDetails userDetails = usersRepository.findByLogin(user.getLogin());
            if (userDetails != null) {
                MessageDto response = new MessageDto("mensagem", "login já castrato");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }else {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
                usersService.creatNewUser(UsersDto.convertToUserModel(user));

                MessageDto response = new MessageDto("mensagem", "usuario cadastrado com sucesso");

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "token valid", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TokenValidation.class)) }),
            @ApiResponse(responseCode = "401", description = "token invalid", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = TokenValidation.class)) }) })
    @PostMapping("validation")
    public ResponseEntity<TokenValidation> validation(@RequestParam String tokenKey){
        String response = tokenService.validateToken(tokenKey);


        return (response.equalsIgnoreCase("Token not validated")) ?
                ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenValidation(StatusToken.VALID, "token has been founded")) :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenValidation(StatusToken.INVALID, "token not found"));



    }
}