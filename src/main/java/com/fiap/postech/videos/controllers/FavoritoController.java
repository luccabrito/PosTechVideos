package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.dto.FavoritoDTO;
import com.fiap.postech.videos.entities.Favorito;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.usecases.favorito.FavoritarVideoUseCase;
import com.fiap.postech.videos.usecases.favorito.ObterVideosFavoritosPorUsernameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritarVideoUseCase favoritarVideoUseCase;

    @Autowired
    private ObterVideosFavoritosPorUsernameUseCase obterVideosFavoritosUseCase;

    @PostMapping("/adicionar")
    public Mono<ResponseEntity<Favorito>> favoritarVideo(@RequestBody FavoritoDTO favoritoDTO) {
        Favorito favorito = favoritoDTO.dtoToEntity(favoritoDTO);
        return favoritarVideoUseCase.executar(favorito)
                .map(favoritoSalvo -> ResponseEntity.created(URI.create("/favoritos/" + favoritoSalvo.getId())).build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Flux<Video>> obterVideosFavoritos(@PathVariable String username) {
        Flux<Video> videosFavoritosDoUsuarioFlux = obterVideosFavoritosUseCase.executar(username);
        return ResponseEntity.ok().body(videosFavoritosDoUsuarioFlux);
    }

}
