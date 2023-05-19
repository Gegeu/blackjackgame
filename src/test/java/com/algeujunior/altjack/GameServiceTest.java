package com.algeujunior.altjack;

import com.algeujunior.altjack.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GameServiceTest {

    GameService gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameService();
    }

    @Test
    @DisplayName("Should initialize cards")
    public void shouldInitializeCards() {
        int sizeOfCardsList = 52;
        var initializedCards = gameService.initializeCards();

        Assertions.assertEquals(sizeOfCardsList, initializedCards.size());
        Assertions.assertNotNull(initializedCards);
    }

    @Test
    @DisplayName("Should initialize deck")
    public void shouldInitializeDeck() {
        var initializedDeck = gameService.initializeDeck();

        Assertions.assertNotNull(initializedDeck);
    }
}
