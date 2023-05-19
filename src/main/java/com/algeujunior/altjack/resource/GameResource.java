package com.algeujunior.altjack.resource;

import com.algeujunior.altjack.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/play")
public class GameResource {

    public GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<Void> print() {
        gameService.initializeDeck();

        return ResponseEntity.ok().build();
    }
}
