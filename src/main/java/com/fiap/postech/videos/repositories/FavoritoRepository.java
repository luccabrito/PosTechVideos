package com.fiap.postech.videos.repositories;

import com.fiap.postech.videos.entities.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FavoritoRepository extends ReactiveMongoRepository<Video, String> {

}
