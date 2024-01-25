package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ObterVideosFavoritosUseCase {

    @Autowired
    private UserRepository userRepository;

    public Flux<Video> executar(String username) {
        return userRepository.findByUsername(username)
                .flatMapMany(user -> Flux.fromIterable(user.getVideosFavoritos()));
    }
}
