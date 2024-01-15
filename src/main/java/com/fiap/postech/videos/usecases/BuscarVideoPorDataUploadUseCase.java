package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Service
public class BuscarVideoPorDataUploadUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Flux<Video> executar(LocalDate dataDeUpload) {
        return videoRepository.findByDataDeUpload(dataDeUpload);
    }
}
