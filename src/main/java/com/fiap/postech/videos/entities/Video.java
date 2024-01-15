package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Video {

    private Long id;
    private String titulo;
    private String descricao;
    private String url;
    private LocalDate dataDeUpload;
    private User uploadedBy;
    private Categorias categoria;
    private Integer totalVisualizacoes;

}
