package com.fiap.postech.videos.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
public class Estatistica {

    private Long totalVideos;
    private Long totalVideosFavoritados;
    private Double mediaVisualizacoes;
}
