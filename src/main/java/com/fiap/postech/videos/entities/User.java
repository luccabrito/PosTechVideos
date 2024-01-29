package com.fiap.postech.videos.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "users")
public class User {

    @Id
    private String username;
    @NotEmpty(message = "Uma senha é requerida.")
    private String senha;

}

