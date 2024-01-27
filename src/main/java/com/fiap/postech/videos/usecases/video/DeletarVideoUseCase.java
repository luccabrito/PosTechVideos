package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeletarVideoUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Mono<Void> executar(String id) {
        return videoRepository.findById(id)
                .flatMap(existingVideo -> videoRepository.delete(existingVideo));
    }
}
