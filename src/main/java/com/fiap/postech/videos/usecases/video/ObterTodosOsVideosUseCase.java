package com.fiap.postech.videos.usecases.video;

import com.fiap.postech.videos.entities.Video;
import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ObterTodosOsVideosUseCase {

    @Autowired
    private VideoRepository videoRepository;

    public Flux<Video> getAllVideos(Pageable pageable) {
        return videoRepository.findAll();
    }

    public Flux<Video> getAllVideosAscendingOrder(Pageable pageable) {
        return videoRepository.findByOrderByDataDeUploadAsc();
    }

    public Flux<Video> getAllVideosDescendingOrder(Pageable pageable) {
        return videoRepository.findByOrderByDataDeUploadDesc();
    }
}
