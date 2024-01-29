package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BuscarVideoPorTituloUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private BuscarVideoPorTituloUseCase buscarVideoPorTituloUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarUmVideoQuandoPossuirApenasUmComStringPesquisadaNoTitulo() {
        var video1 = Video.builder()
                .titulo("Ração para gatos")
                .build();
        var video2 = Video.builder()
                .titulo("Cachorro pulando")
                .build();

        Mockito.when(videoRepository.findByTituloContainingIgnoreCase("Gato")).thenReturn(Flux.just(video1));

        Flux<Video> resultadoReal = buscarVideoPorTituloUseCase.executar("Gato");

        StepVerifier.create(resultadoReal)
                .expectNext(video1)
                .verifyComplete();

        verify(videoRepository, times(1)).findByTituloContainingIgnoreCase("Gato");
    }

    @Test
    public void deveRetornarTresVideosQuandoPossuirTresComStringPesquisadaNoTitulo() {
        var video1 = Video.builder()
                .titulo("Ração para gatos")
                .build();
        var video2 = Video.builder()
                .titulo("Cachorro pulando")
                .build();
        var video3 = Video.builder()
                .titulo("Gato malhado")
                .build();
        var video4 = Video.builder()
                .titulo("Como fazer uma cama de gato")
                .build();

        Mockito.when(videoRepository.findByTituloContainingIgnoreCase("Gato")).thenReturn(Flux.just(video1, video3, video4));

        Flux<Video> resultadoReal = buscarVideoPorTituloUseCase.executar("Gato");

        StepVerifier.create(resultadoReal)
                .expectNext(video1, video3, video4)
                .verifyComplete();

        verify(videoRepository, times(1)).findByTituloContainingIgnoreCase("Gato");
    }

    @Test
    public void deveRetornarDoisVideosIndependenteDoUppercase() {
        var video1 = Video.builder()
                .titulo("Ração para GATO")
                .build();
        var video2 = Video.builder()
                .titulo("gATo brincando")
                .build();

        Mockito.when(videoRepository.findByTituloContainingIgnoreCase("Gato")).thenReturn(Flux.just(video1, video2));

        Flux<Video> resultadoReal = buscarVideoPorTituloUseCase.executar("Gato");

        StepVerifier.create(resultadoReal)
                .expectNext(video1, video2)
                .verifyComplete();

        verify(videoRepository, times(1)).findByTituloContainingIgnoreCase("Gato");
    }
    @Test
    public void deveRetornarFluxVazioQuandoNaoPossuirVideosComStringPesquisada() {
        var video1 = Video.builder()
                .titulo("Ração para gato")
                .build();
        var video2 = Video.builder()
                .titulo("Gato brincando")
                .build();

        Mockito.when(videoRepository.findByTituloContainingIgnoreCase("Cachorro")).thenReturn(Flux.empty());
        Flux<Video> resultadoReal = buscarVideoPorTituloUseCase.executar("Cachorro");
        StepVerifier.create(resultadoReal)
                .verifyComplete();

        verify(videoRepository, times(1)).findByTituloContainingIgnoreCase("Cachorro");
    }
}