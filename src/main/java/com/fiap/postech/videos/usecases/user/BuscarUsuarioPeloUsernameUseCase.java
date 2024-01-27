package com.fiap.postech.videos.usecases.user;

import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BuscarUsuarioPeloUsernameUseCase {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> executar(String username) {
        return userRepository.findByUsername(username);
    }
}
