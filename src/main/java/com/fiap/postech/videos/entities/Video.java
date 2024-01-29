package com.fiap.postech.videos.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Random;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "videos")
public class Video {

    @Id
    private String id;
    @NotEmpty(message = "Título não informado.")
    private String titulo;
    private String descricao;
    @NotEmpty(message = "URL não informada.")
    private String url;
    @Builder.Default //Regra de negócio: Garantir que a data já vai vir preenchida com a data corrente.
    private LocalDate dataDeUpload = LocalDate.now();
    @DBRef @NotEmpty(message = "Usuário não informado.")
    private User uploadedBy;
    private Categoria categoria;
    @Builder.Default
    private Integer totalVisualizacoes = 0;

    public static Video gerarVideo(int id){
        Random random = new Random();
        Integer randomNumber = random.nextInt(1000001);
        return Video.builder()
                .titulo("Titulo_".concat(String.valueOf(id)))
                .descricao("Descrição_".concat(String.valueOf(id)))
                .url("http://video_".concat(String.valueOf(id)))
                .uploadedBy(User.gerarUser(1))
                .totalVisualizacoes(randomNumber)
                .build();
    }

}
