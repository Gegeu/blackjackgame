package com.algeujunior.altjack.service;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.Deck;
import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class DeckServiceTest {

    DeckService deckService;

    @BeforeEach
    public void setUp() {
        deckService = new DeckService();
    }

    @Test
    @DisplayName("Should initialize cards")
    public void shouldInitializeCards() {
        int sizeOfCardsList = 52;
        var initializedCards = deckService.initializeCards();

        Assertions.assertEquals(sizeOfCardsList, initializedCards.size());
        Assertions.assertNotNull(initializedCards);
    }

    @Test
    @DisplayName("Should initialize deck")
    public void shouldInitializeDeck() {
        var initializedDeck = deckService.initializeDeck();

        Assertions.assertNotNull(initializedDeck);
    }

    @Test
    @DisplayName("Should deal a card")
    public void shouldDealCard() {
        var deck = deckMock();
        var cardsList = deck.getCards();
        var sizeOfDeck = cardsList.size() - 1;
        var dealtCard = deckService.dealCard(deck);

        Assertions.assertNotNull(dealtCard);
        Assertions.assertEquals(sizeOfDeck, cardsList.size());
    }

    @Test
    @DisplayName("Should count a card")
    public void shouldCountCard() {
        var lowCountCard = cardsListMock().get(1);
        int expectCardValue = 8;
        var cardValue = deckService.checkCardCount(lowCountCard);

        Assertions.assertEquals(expectCardValue, cardValue);
    }

    @Test
    @DisplayName("Should count highest card")
    public void shouldHighestCountCard() {
        var lowCountCard = highestCardMock();
        int expectCardValue = 10;
        var cardValue = deckService.checkCardCount(lowCountCard);

        Assertions.assertEquals(expectCardValue, cardValue);
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

    private Card highestCardMock() {
        Card card = new Card(Suit.DIAMONDS, Rank.KING);

        return card;
    }

}
