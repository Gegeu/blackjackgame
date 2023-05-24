package com.algeujunior.altjack.resource;

import com.algeujunior.altjack.domain.Score;
import com.algeujunior.altjack.domain.dto.request.ScoreDTORequest;
import com.algeujunior.altjack.domain.dto.response.RoundDTOResponse;
import com.algeujunior.altjack.exception.response.ExceptionResponse;
import com.algeujunior.altjack.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/games")
@Tag(name = "Game", description = "Controller to play the game")
public class GameResource {

    public GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(
            summary = "Create a new game",
            description ="Create a new game for a player")
    @ApiResponses({
            @ApiResponse(responseCode = "201", headers = {@Header(name = "Location", description = "URL of created game")}),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }) })
    @PostMapping
    public ResponseEntity<Void> createGame() {
        var id = gameService.initNewGame();
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).build();
    }

    @Operation(
            summary = "Play a round",
            description ="Play a round for an existing game")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = RoundDTOResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }) })
    @PatchMapping("/{gameId}")
    public ResponseEntity<RoundDTOResponse> playRound(@PathVariable String gameId) {
        var roundDTOResponse = gameService.playRound(gameId);

        return ResponseEntity.ok(roundDTOResponse);
    }

    @Operation(
            summary = "Delete a game",
            description ="Delete a game from database")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }) })
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> finish(@PathVariable String gameId) {
        gameService.finishGame(gameId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Save player score",
            description ="Save player score from a game")
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }) })
    @PostMapping("/{gameId}/results")
    public ResponseEntity<Void> saveResult(@RequestBody ScoreDTORequest resultDTORequest, @PathVariable String gameId) {
        gameService.savePlayerResult(resultDTORequest, gameId);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "List score results",
            description ="List all score results from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = Score.class)), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ExceptionResponse.class)) }) })
    @GetMapping("/results")
    public ResponseEntity<List<Score>> getResults() {
        var resultsList = gameService.getResultScores();

        return ResponseEntity.ok(resultsList);
    }
}
