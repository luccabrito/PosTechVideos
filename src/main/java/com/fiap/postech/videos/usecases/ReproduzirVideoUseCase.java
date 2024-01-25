package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReproduzirVideoUseCase {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private BuscarVideoPorIdUseCase buscarVideoPorIdUseCase;

    @Autowired
    private AtualizarVideoUseCase atualizarVideoUseCase;

    public Mono<Video> executar(String videoId) {
        return videoRepository.findById(videoId)
                .flatMap(video -> {
                    // Incrementa o totalVisualizacoes
                    video.setTotalVisualizacoes(video.getTotalVisualizacoes() + 1);
                    // Salva o vídeo atualizado no MongoDB
                    return videoRepository.save(video);
                });
    }
}