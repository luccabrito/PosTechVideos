package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import com.fiap.postech.videos.repositories.UserRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.when;

public class AllUseCasePackageEstatistica {

    @Mock
    private VideoRepository videoRepository;
    @Mock
    private FavoritoRepository favoritoRepository;
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
        //favoritoRepository.save()

        //when(videoRepository.findAll()).thenReturn()
    }
    @Test
    void deveObterEstatisticasUseCase(){
        //fail("Teste não implementado");
    }

    private Video gerarVideo(int id){
        return Video.builder()
                .titulo("Titulo_".concat(String.valueOf(id)))
                .descricao("Descrição_".concat(String.valueOf(id)))
                .url("http://video_".concat(String.valueOf(id)))
                .uploadedBy(User.gerarUser(1))
                .build();
    }

}
