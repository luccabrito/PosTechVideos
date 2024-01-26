package com.fiap.postech.videos.dto;

import com.fiap.postech.videos.entities.Categoria;
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
    private String uploadedBy;
    private Categoria categoria;
    private Integer totalVisualizacoes;

    public Video dtoToEntity(VideoDTO videoDTO) {
        Video novoVideo = new Video();
        novoVideo.setDataDeUpload(videoDTO.getDataDeUpload());
        novoVideo.setTitulo(videoDTO.getTitulo());
        novoVideo.setCategoria(videoDTO.getCategoria());
        novoVideo.setUrl(videoDTO.getUrl());
        novoVideo.setDescricao(videoDTO.getDescricao());
        novoVideo.setTotalVisualizacoes(videoDTO.getTotalVisualizacoes());
        return novoVideo;
    }


}
