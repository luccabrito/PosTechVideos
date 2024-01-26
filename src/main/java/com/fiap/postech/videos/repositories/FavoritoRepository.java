package com.fiap.postech.videos.repositories;

import com.fiap.postech.videos.entities.Favorito;
import com.fiap.postech.videos.entities.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoritoRepository extends ReactiveMongoRepository<Favorito, String> {

    Flux<Favorito> findByUsername(String username);
    Mono<Long> count();
}
