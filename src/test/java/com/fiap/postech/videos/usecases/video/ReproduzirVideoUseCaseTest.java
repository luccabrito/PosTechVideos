package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReproduzirVideoUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private ReproduzirVideoUseCase reproduzirVideoUseCase;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveAumentarOTotalDeReproducoesEmUm() {
        var video = Video.builder()
                .id("1122")
                .totalVisualizacoes(15)
                .build();

        Mockito.when(videoRepository.findById("1122")).thenReturn(Mono.just(video));
        Mockito.when(videoRepository.save(video)).thenReturn(Mono.just(video));

        Mono<Video> resultadoReal = reproduzirVideoUseCase.executar("1122");

        StepVerifier.create(resultadoReal)
                .expectNextMatches(updatedVideo -> {
                    Assertions.assertEquals(Integer.valueOf(16), updatedVideo.getTotalVisualizacoes());
                    return true;
                })
                .verifyComplete();

        verify(videoRepository, times(1)).findById("1122");
        verify(videoRepository, times(1)).save(video);
    }

}
