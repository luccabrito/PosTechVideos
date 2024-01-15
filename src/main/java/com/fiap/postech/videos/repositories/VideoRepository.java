package com.fiap.postech.videos.repositories;

import com.fiap.postech.videos.entities.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface VideoRepository extends ReactiveMongoRepository<Video, Long> {

    Flux<Video> findAll();

    Mono<Video> findByNome(String nome);

    Flux<Video> findByDataDeUpload(LocalDate dataDeUpload);

    Flux<Video> findByTituloContainingIgnoreCase(String titulo);

}
