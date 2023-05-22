package com.algeujunior.altjack.resource;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.dto.response.PlayerDTOResponse;
import com.algeujunior.altjack.domain.dto.response.RoundDTOResponse;
import com.algeujunior.altjack.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/play")
public class GameResource {

    public GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<String> init() {
        var id = gameService.initNewGame();

        return ResponseEntity.ok(id);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<RoundDTOResponse> play(@PathVariable String gameId) {
        var roundDTOResponse = gameService.play(gameId);

        return ResponseEntity.ok(roundDTOResponse);
    }
}
