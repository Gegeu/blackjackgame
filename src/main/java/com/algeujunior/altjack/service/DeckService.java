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
public class DeckService {

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
        shuffleCards(cards);

        return cards;
    }

    public void shuffleCards(List<Card> cards) {
        Collections.shuffle(cards);
    }

    public Card dealCard(Deck deck) {
        var deckCards = deck.getCards();
        int randomCardIndex = (int) (Math.random() * deckCards.size());
        var cardToDeal = deckCards.get(randomCardIndex);
        deckCards.remove(randomCardIndex);

        return cardToDeal;
    }

    public int checkCardCount(Card playerCard) {
        int countLimit = 10;
        int ordinalCount = playerCard.getRank().ordinal() + 1;
        if(ordinalCount > countLimit) {
            return countLimit;
        }

        return ordinalCount;
    }

}
