package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document (collection = "favoritos")
public class Favorito {

    @Id
    private String id;
    private User user;
    private List<Video> videosFavoritos;

}