package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BuscarVideoPorIdUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Video executar(String id) {
        Mono<Video> monoVideo = videoRepository.findById(id);
        return monoVideo.block();
    }
}
