package com.algeujunior.altjack.domain.dto.response;

import com.algeujunior.altjack.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDTOResponse {

    private int score;
    private List<Card> cards;
    private boolean isDealer;
}
