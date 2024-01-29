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
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BuscarVideosPorCategoriaUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private BuscarVideosPorCategoriaUseCase buscarVideosPorCategoriaUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarDoisVideosComAMesmaCategoria() {
        var video1 = Video.builder()
                .categoria(Categoria.ALIMENTACAO)
                .build();

        var video2 = Video.builder()
                .categoria(Categoria.ALIMENTACAO)
                .build();

        var video3 = Video.builder()
                .categoria(Categoria.ESPORTES)
                .build();

        Mockito.when(videoRepository.findByCategoria(Categoria.ALIMENTACAO)).thenReturn(Flux.just(video1, video2));

        Flux<Video> resultadoReal = buscarVideosPorCategoriaUseCase.executar(Categoria.ALIMENTACAO);

        StepVerifier.create(resultadoReal)
                .expectNext(video1, video2)
                .verifyComplete();

        verify(videoRepository, times(1)).findByCategoria(Categoria.ALIMENTACAO);
    }

    @Test
    public void deveRetornarUmVideoComCategoriaTrivia() {
        var video = Video.builder()
                .categoria(Categoria.TRIVIA)
                .build();

        Mockito.when(videoRepository.findByCategoria(Categoria.TRIVIA)).thenReturn(Flux.just(video));

        Flux<Video> resultadoReal = buscarVideosPorCategoriaUseCase.executar(Categoria.TRIVIA);

        StepVerifier.create(resultadoReal)
                .expectNext(video)
                .verifyComplete();

        verify(videoRepository, times(1)).findByCategoria(Categoria.TRIVIA);
    }

    @Test
    public void deveRetornarFluxVazioQuandoNaoPossuirVideosComCategoriaPesquisada() {
        var video1 = Video.builder()
                .categoria(Categoria.EDUCACAO)
                .build();
        var video2 = Video.builder()
                .categoria(Categoria.FILME)
                .build();

        Mockito.when(videoRepository.findByCategoria(Categoria.ESPORTES)).thenReturn(Flux.empty());
        Flux<Video> resultadoReal = buscarVideosPorCategoriaUseCase.executar(Categoria.ESPORTES);
        StepVerifier.create(resultadoReal)
                .verifyComplete();

        verify(videoRepository, times(1)).findByCategoria(Categoria.ESPORTES);
    }

}
