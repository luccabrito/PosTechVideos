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

    public static User gerarUser(int id){
        return User.builder()
                .username("username_".concat(String.valueOf(id)))
                .senha("sernha_".concat(String.valueOf(id)).concat("@123456"))
                .build();
    }

}

