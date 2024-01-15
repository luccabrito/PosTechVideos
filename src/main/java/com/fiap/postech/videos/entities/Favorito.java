package com.fiap.postech.videos.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Favorito {

    private Long id;
    private User user;
    private List<Video> videosFavoritos;

}