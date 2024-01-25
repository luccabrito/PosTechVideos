package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.UserRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FavoritarVideoUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public Mono<User> executar(String username, String videoId) {
        System.out.println("Não está chegando nessa linha1!");
        return userRepository.findByUsername(username)
                .flatMap(user -> {
                    System.out.println("Não está chegando nessa linha2!");
                    // Encontrar o vídeo pelo ID
                    return videoRepository.findById(videoId)
                            .flatMap(video -> {
                                // Adicionar o vídeo à lista de favoritos do usuário
                                user.getVideosFavoritos().add(video);
                                System.out.println(user.getUsername());
                                System.out.println(video.getId());
                                System.out.println("Não está chegando nessa linha!");
                                // Salvar as alterações no usuário
                                return userRepository.save(user);
                            });
                });
    }
}
