package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ObterTodosOsVideosUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private ObterTodosOsVideosUseCase obterTodosOsVideosUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarTresVideosQuandoTiverTresNoBancoDeDados() {
        var video1 = Video.builder()
                .titulo("Video 1")
                .build();

        var video2 = Video.builder()
                .titulo("Video 2")
                .build();

        var video3 = Video.builder()
                .titulo("Video 3")
                .build();

        Mockito.when(videoRepository.findAll()).thenReturn(Flux.just(video1, video2, video3));

        Pageable pageable = PageRequest.of(0, 5);
        Flux<Video> resultado = obterTodosOsVideosUseCase.getAllVideos(pageable);

        StepVerifier.create(resultado)
                .expectNext(video1, video2, video3)
                .verifyComplete();

        verify(videoRepository, times(1)).findAll();
    }

    @Test
    public void deveRetornarVideoMaisAntigoPrimeiroComMetodoFindByOrderByDataDeUploadAsc() {
        var video1 = Video.builder()
                .titulo("Video mais antigo")
                .dataDeUpload(LocalDate.of(2015, 10, 10))
                .build();

        var video2 = Video.builder()
                .titulo("Video intermediário")
                .dataDeUpload(LocalDate.of(2019, 10, 10))
                .build();

        var video3 = Video.builder()
                .titulo("Video mais recente")
                .dataDeUpload(LocalDate.of(2023, 10, 10))
                .build();

        Mockito.when(videoRepository.findByOrderByDataDeUploadAsc()).thenReturn(Flux.just(video1, video2, video3));

        Pageable pageable = PageRequest.of(0, 5);
        Flux<Video> resultadoReal = obterTodosOsVideosUseCase.getAllVideosAscendingOrder(pageable);
        StepVerifier.create(resultadoReal)
                .expectNext(video1, video2, video3)
                .verifyComplete();
    }

    @Test
    public void deveRetornarVideoMaisNovoPrimeiroComMetodoFindByOrderByDataDeUploadDesc() {
        var video1 = Video.builder()
                .titulo("Video mais antigo")
                .dataDeUpload(LocalDate.of(2015, 10, 10))
                .build();

        var video2 = Video.builder()
                .titulo("Video intermediário")
                .dataDeUpload(LocalDate.of(2019, 10, 10))
                .build();

        var video3 = Video.builder()
                .titulo("Video mais recente")
                .dataDeUpload(LocalDate.of(2023, 10, 10))
                .build();

        Mockito.when(videoRepository.findByOrderByDataDeUploadDesc()).thenReturn(Flux.just(video3, video2, video1));

        Pageable pageable = PageRequest.of(3, 5);
        Flux<Video> resultadoReal = obterTodosOsVideosUseCase.getAllVideosDescendingOrder(pageable);
        StepVerifier.create(resultadoReal)
                .expectNext(video3, video2, video1)
                .verifyComplete();
    }
}
