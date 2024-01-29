package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AllUseCasePackageUserTests {

    @Mock
    private UserRepository userRepository;

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
    void devePermitirCriarUser(){
        // Arrange
       var user = gerarUser();
       user.setUsername("User Name 01");

        //when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        //Act
        var userCriado = userRepository.save(user);

        //Assert
        assertThat(userCriado)
                .isNotNull();
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    void deveBuscarUsuarioPleloUsername(){
        // Arrange
        var user = gerarUser();
        user.setUsername("Username 01");

        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Mono.just(user));

        // Act
        Mono<User> userRetornadoMono = userRepository.findByUsername(user.getUsername());

        // Asset
        assertThat(userRetornadoMono)
            .isNotNull();
        StepVerifier.create(userRetornadoMono)
            .expectNextCount(0)//Gera "expectation "assertNext" failed" caso não retorne nada
            .assertNext(userRetornado -> { //Valida se retornou com o user name correto
                assertThat(userRetornado.getUsername()).isEqualTo(user.getUsername());
                assertThat(userRetornado.getSenha()).isEqualTo(user.getSenha());
            })
            .verifyComplete(); //Verifica se o Mono finalizou com todos os valores esperados.
        verify(userRepository, times(1)).findByUsername(any(String.class));
    }

    private User gerarUser(){
        return User.builder()
                .senha("user01@123456")
                .build();
    }

}
