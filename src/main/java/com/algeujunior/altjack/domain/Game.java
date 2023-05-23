package com.algeujunior.altjack.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Game")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    private String id;
    private Player player;
    private Player dealer;
    private Deck deck;
}
