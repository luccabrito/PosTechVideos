package com.fiap.postech.videos.usecases.favorito;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ObterVideosFavoritosPorUsernameUseCase {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Flux<Video> executar(String username) {
        return favoritoRepository.findByUsername(username)
                .flatMap(favorito -> videoRepository.findById(favorito.getVideoId()));
    }
}
