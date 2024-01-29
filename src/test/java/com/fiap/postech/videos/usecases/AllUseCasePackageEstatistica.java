package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Estatistica;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import com.fiap.postech.videos.usecases.estatistica.ObterEstatisticasUseCase;
import com.fiap.postech.videos.usecases.video.ObterTodosOsVideosUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AllUseCasePackageEstatistica {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private FavoritoRepository favoritoRepository;
    @Mock
    private ObterTodosOsVideosUseCase obterTodosOsVideosUseCase;
    @InjectMocks
    private ObterEstatisticasUseCase obterEstatisticasUseCase;


    AutoCloseable openMocks;

    @BeforeEach //Ao iniciar um teste, devem ser carregados todos os mocks;
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }
    @AfterEach //Ao finalizar um teste, todos os mocks devem ser desalocados da memória.
    void tearDown() throws Exception{
        openMocks.close();
    }
    @Test
    void deveCalcularTotalReproducoes(){
        //Arrenge
        Video video1 = Video.gerarVideo(1);
        video1.setTotalVisualizacoes(200);
        Video video2 = Video.gerarVideo(2);
        video2.setTotalVisualizacoes(300);

        when( obterTodosOsVideosUseCase.getVideos() ).thenReturn(Flux.just(video1, video2));

        //act
        Mono<Long> totalReproducoesMono = obterEstatisticasUseCase.calcularTotalReproducoes();

        //Asset
        StepVerifier.create(totalReproducoesMono)
                .expectNextCount(0)//Gera "expectation "assertNext" failed" caso não retorne nada
                .assertNext(totalReproducoes ->{
                    assertThat(totalReproducoes.longValue()).isEqualTo(
                video1.getTotalVisualizacoes() +
                        video2.getTotalVisualizacoes()
                    );
                })
                .verifyComplete();
    }
    @Test
    void deveObterEstatisticasUseCase(){
        //Arrenge
        Video video1 = Video.gerarVideo(1);
        video1.setTotalVisualizacoes(200);
        Video video2 = Video.gerarVideo(2);
        video2.setTotalVisualizacoes(300);
        Double mediaVisualizacoes = (double) (
                ( video1.getTotalVisualizacoes() + video2.getTotalVisualizacoes() )
                        / 2
        );
        when( obterTodosOsVideosUseCase.getVideos() ).thenReturn(Flux.just(video1, video2));
        when( videoRepository.count() ).thenReturn( Mono.just(2L) );
        when( favoritoRepository.count() ).thenReturn( Mono.just(2L) );

        //act
        Estatistica estatisticaRetornada = obterEstatisticasUseCase.executar();

        //Asset
        assertThat(estatisticaRetornada)
                .isNotNull();
        assertThat(estatisticaRetornada.getMediaVisualizacoes())
                .isEqualTo(mediaVisualizacoes);
        assertThat(estatisticaRetornada.getTotalVideos())
                .isEqualTo(2);
    }



}
