package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String login;
    private String senha;
    private Favorito videosFavoritos;

}
