package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Categoria;
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

import java.time.LocalDate;

import static org.mockito.Mockito.times;

public class CriarVideoUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private CriarVideoUseCase criarVideoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveCriarUmVideo() {
        Video video = Video.builder()
                .titulo("Show de forró")
                .categoria(Categoria.MUSICA)
                .url("https://www.videos.com.br/show-forro")
                .descricao("Um show de forró!")
                .id("123456")
                .dataDeUpload(LocalDate.of(2023, 9, 12))
                .totalVisualizacoes(30000)
                .build();

        Mockito.when(videoRepository.save(video)).thenReturn(Mono.just(video));

        Mono<Video> resultMono = criarVideoUseCase.executar(video);

        StepVerifier.create(resultMono)
                .expectNext(video)
                .verifyComplete();

        Mockito.verify(videoRepository, times(1)).save(video);
    }
}
