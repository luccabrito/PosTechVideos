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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class AtualizarVideoUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private AtualizarVideoUseCase atualizarVideoUseCase;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveAtualizarAsInformacoesDeUmVideoExistente() {
        var videoAntigo = Video.builder()
                .id("112233")
                .url("https://www.videos.com.br/url_antiga")
                .titulo("Titulo antigo")
                .descricao("Descrição antiga")
                .dataDeUpload(LocalDate.of(2021, 4, 30))
                .categoria(Categoria.EDUCACAO)
                .build();

        var videoNovo = Video.builder()
                .id("112233")
                .url("https://www.videos.com.br/url_nova")
                .titulo("Titulo novo")
                .descricao("Descrição nova")
                .dataDeUpload(LocalDate.of(2023, 9, 21))
                .categoria(Categoria.HUMOR)
                .build();

        Mockito.when(videoRepository.findById("112233")).thenReturn(Mono.just(videoAntigo));
        Mockito.when(videoRepository.save(any())).thenReturn(Mono.just(videoNovo));

        Mono<Video> resultMono = atualizarVideoUseCase.executar("112233", videoNovo);

        StepVerifier.create(resultMono)
                .expectNextMatches(updatedVideo -> {
                    // Verificar individualmente os atributos do vídeo atualizado
                    assertEquals("Titulo novo", updatedVideo.getTitulo());
                    assertEquals("Descrição nova", updatedVideo.getDescricao());
                    assertEquals(Categoria.HUMOR, updatedVideo.getCategoria());
                    assertEquals("https://www.videos.com.br/url_nova", updatedVideo.getUrl());
                    assertEquals(LocalDate.of(2023, 9, 21), updatedVideo.getDataDeUpload());
                    return true;
                })
                .verifyComplete();

        Mockito.verify(videoRepository, times(1)).findById("112233");
        Mockito.verify(videoRepository, times(1)).save(any());
    }

    @Test
    public void deveRetornarUmMonoVazioQuandoNaoExistirVideoComId() {
        Video video = new Video();
        Mockito.when(videoRepository.findById("928982")).thenReturn(Mono.empty());
        Mono<Video> resultadoReal = atualizarVideoUseCase.executar("928982", video);
        StepVerifier.create(resultadoReal)
                .verifyComplete();

        verify(videoRepository, times(1)).findById("928982");
    }
}
