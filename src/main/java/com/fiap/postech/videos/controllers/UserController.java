package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.dto.UserDTO;
import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.usecases.user.CriarUserUseCase;
import com.fiap.postech.videos.usecases.user.ObterTodosOsUsersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CriarUserUseCase criarUserUseCase;

    @Autowired
    private ObterTodosOsUsersUseCase obterTodosOsUsersUseCase;

    @PostMapping("/cadastrar")
    public Mono<ResponseEntity<User>> criarUser(@RequestBody UserDTO userDTO) {
        User user = userDTO.dtoToEntity(userDTO);
        return criarUserUseCase.executar(user)
                .map(userSalvo -> ResponseEntity.created(URI.create("/user/" + userSalvo.getUsername())).build());
    }

    @GetMapping
    public ResponseEntity<Flux<User>> buscarTodos() {
        Flux<User> users = obterTodosOsUsersUseCase.executar();
        return ResponseEntity.ok().body(users);
    }
}
