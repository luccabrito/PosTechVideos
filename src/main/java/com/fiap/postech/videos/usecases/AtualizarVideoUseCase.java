package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AtualizarVideoUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Mono<Video> executar(String id, Video videoAtualizado) {
        return videoRepository.findById(id).flatMap(existingVideo -> {
            // Atualiza os campos do vídeo existente com base nos dados do vídeoAtualizado
            existingVideo.setTitulo(videoAtualizado.getTitulo());
            existingVideo.setDescricao(videoAtualizado.getDescricao());
            existingVideo.setDataDeUpload(videoAtualizado.getDataDeUpload());
            existingVideo.setCategoria(videoAtualizado.getCategoria());
            return videoRepository.save(existingVideo);
        });
    }

}
