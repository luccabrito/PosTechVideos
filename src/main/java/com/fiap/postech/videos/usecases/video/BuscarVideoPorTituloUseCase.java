package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class BuscarVideoPorTituloUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Flux<Video> executar(String titulo) {
        return videoRepository.findByTituloContainingIgnoreCase(titulo);
    }
}
