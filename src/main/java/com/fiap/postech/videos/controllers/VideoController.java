package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import com.fiap.postech.videos.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private AtualizarVideoUseCase atualizarVideoUseCase;
    @Autowired
    private BuscarVideoPorDataUploadUseCase buscarVideoPorDataUploadUseCase;
    @Autowired
    private BuscarVideoPorTituloUseCase buscarVideoPorTituloUseCase;
    @Autowired
    private BuscarVideosPorCategoriaUseCase buscarVideosPorCategoriaUseCase;
    @Autowired
    private CriarVideoUseCase criarVideoUseCase;
    @Autowired
    private DeletarVideoUseCase deletarVideoUseCase;
    @Autowired
    private ObterTodosOsVideosUseCase obterTodosOsVideosUseCase;
    @Autowired
    private OrdenarVideosDataPublicacaoUseCase ordenarVideosDataPublicacaoUseCase;

    private VideoRepository videoRepository;

    @GetMapping
    public ResponseEntity<Flux<Video>> obterTodosOsVideos() {
        Flux<Video> videos = obterTodosOsVideosUseCase.getAllVideos();
        return ResponseEntity.ok().body(videos);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Video>> atualizarVideo(@PathVariable Long id, @RequestBody Video videoAtualizado) {
        return atualizarVideoUseCase.executar(id, videoAtualizado)
                .map(updatedVideo -> ResponseEntity.ok(updatedVideo))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorDataDeUpload")
    public Mono<ResponseEntity<List<Video>>> buscarPorDataDeUpload(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDeUpload) {
        Flux<Video> videosFlux = buscarVideoPorDataUploadUseCase.executar(dataDeUpload);
        return videosFlux
                .collectList()
                .map(videos -> !videos.isEmpty() ? ResponseEntity.ok(videos) : ResponseEntity.noContent().build());
    }

    @GetMapping("/buscarPorTitulo")
    public ResponseEntity<Flux<Video>> buscarPorTitulo(@RequestParam String titulo) {
        Flux<Video> videos = buscarVideoPorTituloUseCase.executar(titulo);
        return ResponseEntity.ok().body(videos);
    }




}
