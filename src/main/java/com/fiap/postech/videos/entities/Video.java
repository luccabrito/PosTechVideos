package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "videos")
public class Video {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;
    private LocalDate dataDeUpload;
    private User uploadedBy;
    private Categoria categoria;
    private Integer totalVisualizacoes;

}
