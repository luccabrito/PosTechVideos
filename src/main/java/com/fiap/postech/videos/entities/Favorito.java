package com.fiap.postech.videos.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Document(collection = "favoritos")
public class Favorito {

    @Id
    private String id;
    @NotEmpty(message = "O username do usuário deve ser informado.")
    private String username;
    @NotEmpty(message = "O videoId deve ser informado informado.")
    private String videoId;

}
