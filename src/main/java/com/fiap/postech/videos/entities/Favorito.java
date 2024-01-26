package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "favoritos")
public class Favorito {

    @Id
    private String id;
    private String username;
    private String videoId;

}
