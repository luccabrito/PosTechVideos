package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.dto.UserDTO;
import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CriarUserUseCase {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> executar(User novoUser) {
        return userRepository.save(novoUser);
    }

}
