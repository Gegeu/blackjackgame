package com.algeujunior.altjack.service;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.Deck;
import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    public Deck initializeDeck() {
        var initializedCards = initializeCards();
        var deckOfCards = new Deck(initializedCards);

        return deckOfCards;
    }

    public List<Card> initializeCards() {
        List<Card> cards = new ArrayList<>();

        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                Card card = new Card(suit, rank);
                cards.add(card);
            }
        }

        return cards;
    }

    public void shuffleCards(List<Card> cards) {
        Collections.shuffle(cards);
    }
}
