package com.algeujunior.altjack.service;

import com.algeujunior.altjack.domain.*;
import com.algeujunior.altjack.domain.dto.request.ScoreDTORequest;
import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import com.algeujunior.altjack.exception.exceptions.CustomEntityNotFoundException;
import com.algeujunior.altjack.repository.GameRepository;
import com.algeujunior.altjack.repository.ScoreRepository;
import com.algeujunior.altjack.service.DeckService;
import com.algeujunior.altjack.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class GameServiceTest {

    GameService gameService;
    @Mock
    DeckService deckService;
    @Mock
    GameRepository gameRepository;
    @Mock
    ScoreRepository scoreRepository;
    @Captor
    ArgumentCaptor<Score> scoreCaptor;

    @BeforeEach
    public void setUp() {
        gameService = new GameService(gameRepository, scoreRepository, deckService);
        ReflectionTestUtils.setField(gameService, "gameNotFoundMessage", "Game ID: %s not found.");
    }

    @Test
    @DisplayName("Should initialize a game")
    public void shouldInitializeGame() {
        var gameId = "123";
        Mockito.when(deckService.initializeDeck()).thenReturn(deckMock());
        Mockito.when(gameRepository.save(Mockito.any())).thenReturn(gameMock());
        var resultGameId = gameService.initNewGame();

        Assertions.assertEquals(gameId, resultGameId);
        Assertions.assertNotNull(resultGameId);
    }

    @Test
    @DisplayName("Should hit a round")
    public void shouldHitRound() {
        var game = gameMock();
        var expectedPlayersSize = 2;
        var playerDTOResponses = gameService.hitRound(game);

        Assertions.assertEquals(expectedPlayersSize, playerDTOResponses.size());
    }

    @Test
    @DisplayName("Should play a round")
    public void shouldPlayRound() {
        var game = gameMock();
        Mockito.when(gameRepository.findById(Mockito.anyString()))
                .thenReturn(java.util.Optional.ofNullable(gameMock()));
        Mockito.when(gameRepository.save(Mockito.any(Game.class)))
                .thenReturn(gameMock());
        var roundDTOResponse = gameService.playRound(game.getId());

        Assertions.assertEquals(false, roundDTOResponse.isEnded());
        Assertions.assertEquals(2, roundDTOResponse.getPlayerDTOResponse().size());
    }

    @Test
    @DisplayName("Should throw when a game not exists")
    public void shouldThrowWhenGameNotExists() {
        var notExistingId = "1";

        Assertions.assertThrows(CustomEntityNotFoundException.class, () -> gameService.findGameIfExists(notExistingId));
    }

    @Test
    @DisplayName("Should finish a game")
    public void shouldFinishGame() {
        var existingId = "123";

        Mockito.when(gameRepository.findById(Mockito.anyString()))
                .thenReturn(java.util.Optional.ofNullable(gameMock()));
        Mockito.doNothing().when(gameRepository).deleteById(Mockito.anyString());
        gameService.finishGame(existingId);

        Mockito.verify(gameRepository, Mockito.times(1)).deleteById(existingId);
    }

    @Test
    @DisplayName("Should save a result")
    public void shouldSaveResult() {
        var score = scoreMock();
        var gameId = "1";
        Mockito.when(scoreRepository.save(scoreCaptor.capture()))
                .thenReturn(score);
        Mockito.when(gameRepository.findById(Mockito.anyString()))
                .thenReturn(java.util.Optional.ofNullable(gameMock()));
        gameService.savePlayerResult(scoreDTORequestMock(), gameId);

        Assertions.assertEquals(scoreDTORequestMock().getPlayerName(), scoreCaptor.getValue().getPlayerName());
    }

    @Test
    @DisplayName("Should get results")
    public void shouldGetResults() {
        var score = scoreMock();
        int sizeScoreList = 1;
        Mockito.when(scoreRepository.findAllByOrderByScoreDesc())
                        .thenReturn(Collections.singletonList(score));
        var resultScore = gameService.getResultScores();

        Assertions.assertEquals(sizeScoreList, resultScore.size());
    }

    private ScoreDTORequest scoreDTORequestMock() {
        return ScoreDTORequest.builder()
                .playerName("Player 2")
                .build();
    }

    private Score scoreMock() {
        return Score.builder()
                .id(1L)
                .playerName("Player 1")
                .score(10)
                .dateTime(getTimeNow())
                .build();
    }

    private LocalDateTime getTimeNow() {
        return LocalDateTime.now();
    }

    private Game gameMock() {
        return Game.builder()
                .id("123")
                .deck(deckMock())
                .player(playerMock())
                .dealer(playerMock())
                .build();
    }

    private Player playerMock() {
        return Player.builder()
                .name("P")
                .score(0)
                .build();
    }

    private Deck deckMock() {
        return new Deck(cardsListMock());
    }

    private List<Card> cardsListMock() {
        Card cardOne = new Card(Suit.CLUBS, Rank.ACE);
        Card carTwo = new Card(Suit.HEARTS, Rank.EIGHT);
        var cards = new ArrayList<>(Arrays.asList(cardOne, carTwo));

        return cards;
    }
}
