package com.fiap.postech.videos.usecases;

import com.fiap.postech.videos.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarVideoUseCase {

    @Autowired
    private VideoRepository videoRepository;


}
