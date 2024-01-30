package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.entities.Categoria;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.usecases.video.BuscarVideoPorDataUploadUseCase;
import com.fiap.postech.videos.usecases.video.BuscarVideoPorTituloUseCase;
import com.fiap.postech.videos.usecases.video.BuscarVideosPorCategoriaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class VideoControllerTest {

    @Mock
    private BuscarVideoPorDataUploadUseCase buscarVideoPorDataUploadUseCase;

    @Mock
    private BuscarVideoPorTituloUseCase buscarVideoPorTituloUseCase;

    @Mock
    private BuscarVideosPorCategoriaUseCase buscarVideosPorCategoriaUseCase;

    @InjectMocks
    private VideoController videoController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void buscarPorDataDeUploadDeveRetornar200QuandoExistemVideosComADataFornecida() {
        LocalDate dataDeUpload = LocalDate.now();
        Video video1 = Video.builder().id("1").titulo("Vídeo 1").dataDeUpload(dataDeUpload).build();
        Video video2 = Video.builder().id("2").titulo("Vídeo 2").dataDeUpload(dataDeUpload).build();
        Mockito.when(buscarVideoPorDataUploadUseCase.executar(dataDeUpload)).thenReturn(Flux.just(video1, video2));

        var resultMono = videoController.buscarPorDataDeUpload(dataDeUpload);

        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
                    Flux<Video> videos = responseEntity.getBody();
                    assertNotNull(videos);
                    Assertions.assertEquals(2, videos.count().block());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void buscarPorDataDeUploadDeveRetornar404SeNaoTiverVideoComADataFornecida() {
        LocalDate dataDeUpload = LocalDate.now();
        Mockito.when(buscarVideoPorDataUploadUseCase.executar(dataDeUpload)).thenReturn(Flux.empty());
        var resultMono = videoController.buscarPorDataDeUpload(dataDeUpload);
        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is4xxClientError());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void buscarPorTituloDeveRetornar200QuandoExistemVideosComOTituloFornecido() {
        Video video1 = Video.builder().id("1").titulo("Vídeo 1 - Gato").build();
        Video video2 = Video.builder().id("2").titulo("Vídeo 2 - Gato").build();
        Mockito.when(buscarVideoPorTituloUseCase.executar("Gato")).thenReturn(Flux.just(video1, video2));

        var resultMono = videoController.buscarPorTitulo("Gato");

        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
                    Flux<Video> videos = responseEntity.getBody();
                    assertNotNull(videos);
                    Assertions.assertEquals(2, videos.count().block());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void buscarPorTituloDeveRetornar404QuandoNaoExistemVideosComOTituloFornecido() {
        Mockito.when(buscarVideoPorTituloUseCase.executar("Qualquer")).thenReturn(Flux.empty());
        var resultMono = videoController.buscarPorTitulo("Qualquer");
        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is4xxClientError());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void buscarPorCategoriaDeveRetornar200QuandoExistemVideosNaCategoriaFornecida() {
        Video video1 = Video.builder().id("1").categoria(Categoria.EDUCACAO).build();
        Video video2 = Video.builder().id("2").categoria(Categoria.EDUCACAO).build();
        Mockito.when(buscarVideosPorCategoriaUseCase.executar(Categoria.EDUCACAO)).thenReturn(Flux.just(video1, video2));

        var resultMono = videoController.buscarPorCategoria(Categoria.EDUCACAO);

        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
                    Flux<Video> videos = responseEntity.getBody();
                    assertNotNull(videos);
                    Assertions.assertEquals(2, videos.count().block());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void buscarPorCategoriaDeveRetornar404QuandoNaoExistemVideosNaCategoriaFornecida() {
        Mockito.when(buscarVideosPorCategoriaUseCase.executar(Categoria.MUSICA)).thenReturn(Flux.empty());
        var resultMono = videoController.buscarPorCategoria(Categoria.MUSICA);
        StepVerifier.create(resultMono)
                .expectNextMatches(responseEntity -> {
                    assertTrue(responseEntity.getStatusCode().is4xxClientError());
                    return true;
                })
                .verifyComplete();
    }
}
