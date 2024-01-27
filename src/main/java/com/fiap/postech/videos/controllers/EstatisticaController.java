package com.fiap.postech.videos.controllers;

import com.fiap.postech.videos.entities.Estatistica;
import com.fiap.postech.videos.usecases.estatistica.ObterEstatisticasUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private ObterEstatisticasUseCase obterEstatisticasUseCase;

    @GetMapping
    public ResponseEntity<Estatistica> obterEstatisticas() {
        Estatistica estatistica = obterEstatisticasUseCase.executar();
        return ResponseEntity.ok().body(estatistica);
    }
}
