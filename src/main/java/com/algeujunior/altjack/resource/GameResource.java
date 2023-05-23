package com.algeujunior.altjack.resource;

import com.algeujunior.altjack.domain.Score;
import com.algeujunior.altjack.domain.dto.request.ScoreDTORequest;
import com.algeujunior.altjack.domain.dto.response.RoundDTOResponse;
import com.algeujunior.altjack.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/games")
public class GameResource {

    public GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Void> createGame() {
        var id = gameService.initNewGame();
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{gameId}")
    public ResponseEntity<RoundDTOResponse> playRound(@PathVariable String gameId) {
        var roundDTOResponse = gameService.playRound(gameId);

        return ResponseEntity.ok(roundDTOResponse);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> finish(@PathVariable String gameId) {
        gameService.finishGame(gameId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{gameId}/results")
    public ResponseEntity<Void> saveResult(@RequestBody ScoreDTORequest resultDTORequest, @PathVariable String gameId) {
        gameService.savePlayerResult(resultDTORequest, gameId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/results")
    public ResponseEntity<List<Score>> getResults() {
        var resultsList = gameService.getResultScores();

        return ResponseEntity.ok(resultsList);
    }
}
