package com.algeujunior.altjack.domain;

import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {

    private Suit suit;
    private Rank Rank;
}
