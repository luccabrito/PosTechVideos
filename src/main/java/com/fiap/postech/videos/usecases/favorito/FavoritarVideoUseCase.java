package com.fiap.postech.videos.usecases.favorito;

import com.fiap.postech.videos.entities.Favorito;
import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import com.fiap.postech.videos.repositories.UserRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FavoritarVideoUseCase {

    @Autowired
    private FavoritoRepository favoritoRepository;

    public Mono<Favorito> executar(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }


}
