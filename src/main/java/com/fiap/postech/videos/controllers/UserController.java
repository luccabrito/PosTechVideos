package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.dto.FavoritarDTO;
import com.fiap.postech.videos.dto.UserDTO;
import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.usecases.user.CriarUserUseCase;
import com.fiap.postech.videos.usecases.video.FavoritarVideoUseCase;
import com.fiap.postech.videos.usecases.user.ObterTodosOsUsersUseCase;
import com.fiap.postech.videos.usecases.video.ObterVideosFavoritosUseCase;
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
    private FavoritarVideoUseCase favoritarVideoUseCase;

    @Autowired
    private ObterVideosFavoritosUseCase obterVideosFavoritosUseCase;

    @Autowired
    private CriarUserUseCase criarUserUseCase;

    @Autowired
    private ObterTodosOsUsersUseCase obterTodosOsUsersUseCase;

    @PostMapping("/favoritar")
    public ResponseEntity<Video> favoritarVideo(@RequestBody FavoritarDTO favoritarDTO) {
        String username = favoritarDTO.getUsername();
        String videoID = favoritarDTO.getVideoId();
        Mono<User> userMono = favoritarVideoUseCase.executar(username, videoID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/videosFavoritos")
    public ResponseEntity<Flux<Video>> obterVideosFavoritos(@PathVariable String username) {
        Flux<Video> videosFavoritosDoUsuarioFlux = obterVideosFavoritosUseCase.executar(username);
        return ResponseEntity.ok().body(videosFavoritosDoUsuarioFlux);
    }

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
