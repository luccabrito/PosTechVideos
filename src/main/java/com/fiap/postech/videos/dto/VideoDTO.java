package com.fiap.postech.videos.dto;

import com.fiap.postech.videos.entities.Categoria;
import com.fiap.postech.videos.entities.User;
import com.fiap.postech.videos.entities.Video;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VideoDTO {
    private String titulo;
    private String descricao;
    private String url;
    private LocalDate dataDeUpload;
    private User uploadedBy;
    private Categoria categoria;

    public Video dtoToEntity(VideoDTO videoDTO) {
        Video novoVideo = new Video();
        novoVideo.setTitulo(videoDTO.getTitulo());
        novoVideo.setCategoria(videoDTO.getCategoria());
        novoVideo.setUrl(videoDTO.getUrl());
        novoVideo.setDescricao(videoDTO.getDescricao());
        novoVideo.setDataDeUpload(videoDTO.getDataDeUpload());
        novoVideo.setUploadedBy(videoDTO.getUploadedBy());
        novoVideo.setTotalVisualizacoes(0);
        return novoVideo;
    }
}
