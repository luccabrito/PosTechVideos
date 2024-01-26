package com.fiap.postech.videos.usecases.estatistica;

import com.fiap.postech.videos.entities.Estatistica;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.FavoritoRepository;
import com.fiap.postech.videos.repositories.VideoRepository;
import com.fiap.postech.videos.usecases.video.ObterTodosOsVideosUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ObterEstatisticasUseCase {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ObterTodosOsVideosUseCase obterTodosOsVideosUseCase;

    public Mono<Long> calcularTotalReproducoes() {
        return obterTodosOsVideosUseCase.getVideos()
                .map(Video::getTotalVisualizacoes)
                .reduce(0L, Long::sum);
    }

    public Estatistica executar() {

        Long totalVideos = videoRepository.count().block();
        Long totalFavoritados = favoritoRepository.count().block();
        Long totalVisualizacoes = calcularTotalReproducoes().block();
        Double mediaVisualizacoes = (double) (totalVisualizacoes / totalVideos);

        return new Estatistica(totalVideos, totalFavoritados, mediaVisualizacoes);
    }
}