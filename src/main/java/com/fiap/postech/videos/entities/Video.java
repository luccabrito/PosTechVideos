package com.fiap.postech.videos.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "videos")
public class Video {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private String url;
    @Builder.Default //Regra de negócio: Garantir que a data já vai vir preenchida com a data corrente.
    private LocalDate dataDeUpload = LocalDate.now();
    @DBRef
    private User uploadedBy;
    private Categoria categoria;
    @Builder.Default
    private Integer totalVisualizacoes = 0;

}
