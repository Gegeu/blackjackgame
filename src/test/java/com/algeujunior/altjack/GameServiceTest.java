package com.algeujunior.altjack;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.Deck;
import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import com.algeujunior.altjack.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameServiceTest {

    GameService gameService;

    @BeforeEach
    public void setup() {
        gameService = new GameService();
    }

    @Test
    @DisplayName("Should initialize the cards")
    public void shouldInitializeCards() {
        int sizeOfCardsList = 52;
        var initializedCards = gameService.initializeCards();

        Assertions.assertEquals(sizeOfCardsList, initializedCards.size());
        Assertions.assertNotNull(initializedCards);
    }

    @Test
    @DisplayName("Should initialize a deck")
    public void shouldInitializeDeck() {
        var initializedDeck = gameService.initializeDeck();

        Assertions.assertNotNull(initializedDeck);
    }

    @Test
    @DisplayName("Should deal a card")
    public void shouldDealCard() {
        var cardsList = deckMock().getCards();
        var sizeOfDeck = cardsList.size() - 1;
        var dealtCard = gameService.dealCard(cardsList);

        Assertions.assertNotNull(dealtCard);
        Assertions.assertEquals(sizeOfDeck, cardsList.size());
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
