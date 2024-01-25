package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String username;
    private String senha;
    private List<Video> videosFavoritos;

}

