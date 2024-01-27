package com.fiap.postech.videos.usecases.user;

import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ObterTodosOsUsersUseCase {

    @Autowired
    private UserRepository userRepository;

    public Flux<User> executar() {
        return userRepository.findAll();
    }
}
