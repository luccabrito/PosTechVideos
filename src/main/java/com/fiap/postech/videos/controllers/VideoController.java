package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.dto.VideoDTO;
import com.fiap.postech.videos.entities.Categoria;
import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.usecases.user.BuscarUsuarioPeloUsernameUseCase;
import com.fiap.postech.videos.usecases.video.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
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
    private BuscarUsuarioPeloUsernameUseCase buscarUsuarioPeloUsernameUseCase;
    @Autowired
    private ReproduzirVideoUseCase reproduzirVideoUseCase;

    @GetMapping
    public ResponseEntity<Flux<Video>> obterTodosOsVideos(@RequestParam(required = false) String order,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Flux<Video> videos;
        Pageable pageable = PageRequest.of(page, size);

        if ("desc".equalsIgnoreCase(order)) {
            videos = obterTodosOsVideosUseCase.getAllVideosDescendingOrder(pageable);
        } else if ("asc".equalsIgnoreCase(order)) {
            videos = obterTodosOsVideosUseCase.getAllVideosAscendingOrder(pageable);
        } else {
            videos = obterTodosOsVideosUseCase.getAllVideos(pageable);
        }

        if (videos != null) {
            return ResponseEntity.ok().body(videos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Video>> atualizarVideo(@PathVariable String id, @RequestBody VideoDTO videoAtualizado) {
        Video video = videoAtualizado.dtoToEntity(videoAtualizado);
        return atualizarVideoUseCase.executar(id, video)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorDataDeUpload")
    public Mono<ResponseEntity<Flux<Video>>> buscarPorDataDeUpload(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDeUpload) {
        Flux<Video> videosFlux = buscarVideoPorDataUploadUseCase.executar(dataDeUpload);
        return videosFlux.hasElements()
                .map(hasElements -> hasElements ? ResponseEntity.ok().body(videosFlux) : ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorTitulo")
    public Mono<ResponseEntity<Flux<Video>>> buscarPorTitulo(@RequestParam String titulo) {
        Flux<Video> videos = buscarVideoPorTituloUseCase.executar(titulo);
        return videos.hasElements()
                .map(hasElements -> hasElements ? ResponseEntity.ok().body(videos) : ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPorCategoria")
    public Mono<ResponseEntity<Flux<Video>>> buscarPorCategoria(@RequestParam Categoria categoria) {
        Flux<Video> videos = buscarVideosPorCategoriaUseCase.executar(categoria);
        return videos.hasElements()
                .map(hasElements -> hasElements ? ResponseEntity.ok().body(videos) : ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Video>> criarVideo(@RequestBody VideoDTO videoDTO) {
        return buscarUsuarioPeloUsernameUseCase.executar(videoDTO.getUploadedBy())
                .flatMap(user -> {
                    Video novoVideo = videoDTO.dtoToEntity(videoDTO);
                    novoVideo.setUploadedBy(user);
                    return criarVideoUseCase.executar(novoVideo)
                            .map(videoSalvo -> ResponseEntity.created(URI.create("/videos/" + videoSalvo.getId())).body(videoSalvo));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build())); // Usuário não encontrado
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletarVideo(@PathVariable String id) {
        return deletarVideoUseCase.executar(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/reproduzir")
    public Mono<ResponseEntity<Video>> reproduzirVideo(@PathVariable String id) {
        var video = reproduzirVideoUseCase.executar(id);
        return video.map(ResponseEntity::ok)
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
