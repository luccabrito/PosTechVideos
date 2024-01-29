package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class DeletarVideoUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private DeletarVideoUseCase deletarVideoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveDeletarVideoDoRepository() {
        String videoId = "123";
        Video video = Video.builder()
                .id(videoId)
                .titulo("Vídeo exemplo")
                .build();

        Mockito.when(videoRepository.findById(videoId)).thenReturn(Mono.just(video));
        Mockito.when(videoRepository.delete(any())).thenReturn(Mono.empty());

        Mono<Void> resultMono = deletarVideoUseCase.executar(videoId);

        StepVerifier.create(resultMono)
                .verifyComplete();

        Mockito.verify(videoRepository, times(1)).findById(videoId);
        Mockito.verify(videoRepository, times(1)).delete(any());
    }

    @Test
    public void deveRetornarUmMonoEmptyCasoNaoHajaVideoComOID() {
        Mockito.when(videoRepository.findById("abc")).thenReturn(Mono.empty());
        Mono<Void> resultMono = deletarVideoUseCase.executar("abc");

        StepVerifier.create(resultMono)
                .verifyComplete();

        Mockito.verify(videoRepository, times(1)).findById("abc");
    }
}
