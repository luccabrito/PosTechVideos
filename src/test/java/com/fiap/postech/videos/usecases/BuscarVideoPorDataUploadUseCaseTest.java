package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import com.fiap.postech.videos.usecases.video.BuscarVideoPorDataUploadUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class BuscarVideoPorDataUploadUseCaseTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private BuscarVideoPorDataUploadUseCase buscarVideoPorDataUploadUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarUmVideoComDataDeUploadCorreta() {

        LocalDate dataDeUpload = LocalDate.of(2023, 9, 12);
        var video1 = Video.builder()
                        .titulo("Vídeo 1")
                        .dataDeUpload( dataDeUpload )
                        .build();

        Mockito.when(videoRepository.findByDataDeUpload(dataDeUpload)).thenReturn(Flux.just(video1));

        Flux<Video> resultadoReal = buscarVideoPorDataUploadUseCase.executar(dataDeUpload);

        StepVerifier.create(resultadoReal)
                .expectNext(video1)
                .verifyComplete();

        verify(videoRepository, times(1)).findByDataDeUpload(dataDeUpload);
    }

    @Test
    public void deveRetornarDoisVideosComDataDeUploadCorreta() {

        Video video1 = new Video();
        video1.setTitulo("Vídeo 1");
        video1.setDataDeUpload(LocalDate.of(2023, 9, 12));

        Video video2 = new Video();
        video2.setTitulo("Vídeo 2");
        video2.setDataDeUpload(LocalDate.of(2023, 9, 12));

        Mockito.when(videoRepository.findByDataDeUpload(LocalDate.of(2023, 9, 12))).thenReturn(Flux.just(video1, video2));

        Flux<Video> resultadoReal = buscarVideoPorDataUploadUseCase.executar(LocalDate.of(2023, 9, 12));

        StepVerifier.create(resultadoReal)
                .expectNext(video1, video2)
                .verifyComplete();
    }

    @Test
    public void deveRetornarFluxVazioQuandoDataNaoPossuirVideo() {
        LocalDate dataSemVideos = LocalDate.of(2022, 1, 1);
        when(videoRepository.findByDataDeUpload(dataSemVideos)).thenReturn(Flux.empty());
        Flux<Video> resultado = buscarVideoPorDataUploadUseCase.executar(dataSemVideos);
        StepVerifier.create(resultado)
                .verifyComplete();
        verify(videoRepository, times(1)).findByDataDeUpload(dataSemVideos);

    }
}
