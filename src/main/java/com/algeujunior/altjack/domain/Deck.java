package com.algeujunior.altjack.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Deck {

    private List<Card> cards;

}
