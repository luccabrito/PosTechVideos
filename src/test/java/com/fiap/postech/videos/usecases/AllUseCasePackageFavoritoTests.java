package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Favorito;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AllUseCasePackageFavoritoTests {

    @Mock
    private FavoritoRepository favoritoRepository;

    AutoCloseable openMocks;

    @BeforeEach
        //Ao iniciar um teste, devem ser carregados todos os mocks;
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
        //Ao finalizar um teste, todos os mocks devem ser desalocados da memória.
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void deveFavoritarVideo(){
        // Arrange
        Favorito favorito = gerarFavorito(1,1);
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(Mono.just(favorito));

        //Act
        Mono<Favorito> favoritoRetornadoMono = favoritoRepository.save(favorito);

        //Assert
        assertThat(favoritoRetornadoMono)
                .isNotNull();
        verify(favoritoRepository, times(1)).save(any(Favorito.class));
    }

    @Test
    void deveObterVideosFavoritosPorUsernameUseCase(){
        //Arrange
        var favorito = gerarFavorito(1,1);
        when(favoritoRepository.findByUsername(any(String.class))).thenReturn(Flux.just(favorito));

        //Act
        Flux<Favorito> favoritoRetornadoFlux = favoritoRepository.findByUsername(favorito.getUsername());

        //Assert
        assertThat(favoritoRetornadoFlux)
                .isNotNull();
        StepVerifier.create(favoritoRetornadoFlux)
                .expectNextCount(0)//Gera "expectation "assertNext" failed" caso não retorne nada
                .assertNext(favoritoRetornado -> {
                    assertThat(favoritoRetornado.getUsername()).isEqualTo(favorito.getUsername());
                })
                .verifyComplete();
    }

    private Favorito gerarFavorito(int userId, int videoId){
        return Favorito.builder()
                .id(String.valueOf(userId))
                .username("username_".concat(String.valueOf(userId)))
                .videoId("videoId_".concat(String.valueOf(videoId)))
                .build();
    }

}
