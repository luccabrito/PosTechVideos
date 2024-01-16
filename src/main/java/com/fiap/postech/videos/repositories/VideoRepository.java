package com.fiap.postech.videos.repositories;

import com.fiap.postech.videos.entities.Categoria;
import com.fiap.postech.videos.entities.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.awt.print.Pageable;
import java.time.LocalDate;

public interface VideoRepository extends ReactiveMongoRepository<Video, String> {

    Flux<Video> findAll();

    Flux<Video> findByDataDeUpload(LocalDate dataDeUpload);

    Flux<Video> findByTituloContainingIgnoreCase(String titulo);

    Flux<Video> findByCategoria(Categoria categoria);
    Flux<Video> findByOrderByDataDeUploadAsc();
    Flux<Video> findByOrderByDataDeUploadDesc();
}
