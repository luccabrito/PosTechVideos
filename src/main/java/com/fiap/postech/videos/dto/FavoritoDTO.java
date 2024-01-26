package com.fiap.postech.videos.dto;

import com.fiap.postech.videos.entities.Favorito;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoritoDTO {
    private String videoId;
    private String username;

    public Favorito dtoToEntity(FavoritoDTO favoritoDTO) {
        Favorito novoFavorito = new Favorito();
        novoFavorito.setVideoId(favoritoDTO.getVideoId());
        novoFavorito.setUsername(favoritoDTO.getUsername());
        return novoFavorito;
    }
}
