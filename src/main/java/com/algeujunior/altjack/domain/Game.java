package com.algeujunior.altjack.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Game")
public class Game {

    @Id
    private String id;
    private Player player;
    private Player dealer;
    private Deck deck;
}
