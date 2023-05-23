package com.algeujunior.altjack.service;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.Deck;
import com.algeujunior.altjack.domain.Game;
import com.algeujunior.altjack.domain.Player;
import com.algeujunior.altjack.domain.dto.response.PlayerDTOResponse;
import com.algeujunior.altjack.domain.dto.response.RoundDTOResponse;
import com.algeujunior.altjack.repository.GameRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    @Value("${message.exception.game-not-found}")
    private String gameNotFoundMessage;
    private GameRepository gameRepository;
    private DeckService deckService;

    public GameService(GameRepository gameRepository, DeckService deckService) {
        this.gameRepository = gameRepository;
        this.deckService = deckService;
    }

    public RoundDTOResponse play(String gameId) {
        String gameNotFoundResponseMessage = String.format(gameNotFoundMessage, gameId);
        var game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException(gameNotFoundResponseMessage));

        var playerDTOResponses = hit(game);
        var roundDTOResponse = getRoundResponse(playerDTOResponses);
        gameRepository.save(game);

        return roundDTOResponse;
    }

    public List<PlayerDTOResponse> hit(Game game) {

        var deckOfCards = game.getDeck();
        boolean isFirstRound = deckOfCards.getCards().size() > 51;
        Player player = game.getPlayer();
        Player dealer = game.getDealer();
        var playersList = new ArrayList<>(Arrays.asList(player, dealer));
        var playerDTOResponses = new ArrayList<PlayerDTOResponse>();

        setPlayerValues(deckOfCards, isFirstRound, playersList, playerDTOResponses);

        return playerDTOResponses;
    }

    public String initNewGame() {
        var deckOfCards = deckService.initializeDeck();
        var game = new Game();
        var player = new Player();
        var dealer = new Player();
        game.setDealer(dealer);
        game.setPlayer(player);
        game.setDeck(deckOfCards);
        var savedGame = gameRepository.save(game);

        return savedGame.getId();
    }

    private void setPlayerValues(Deck deckOfCards, boolean isFirstRound, ArrayList<Player> playersList, ArrayList<PlayerDTOResponse> playerDTOResponses) {
        for(int i = 0; i < playersList.size(); i++) {
            boolean isFirstPlayer = i == 0;
            boolean setAsDealerIfFirst = isFirstPlayer ? true : false;
            var actualPlayer = playersList.get(i);
            var playerCard = deckService.dealCard(deckOfCards);
            setActualScore(actualPlayer, playerCard);
            var playerDTOResponse = getPlayerDTOResponses(actualPlayer, playerCard, setAsDealerIfFirst);
            playerDTOResponses.add(playerDTOResponse);
            checkIfIsFirstRound(deckOfCards, isFirstRound, playerDTOResponses, i, actualPlayer);
        }
    }

    private void checkIfIsFirstRound(Deck deckOfCards, boolean isFirstRound, ArrayList<PlayerDTOResponse> playerDTOResponses, int i, Player actualPlayer) {
        if(isFirstRound) {
            var playerSecondCard = deckService.dealCard(deckOfCards);
            setActualScore(actualPlayer, playerSecondCard);
            var actualPlayerDTOResponse = playerDTOResponses.get(i);
            var actualPlayerCards = actualPlayerDTOResponse.getCards();
            actualPlayerCards.add(playerSecondCard);
            int updatedPlayerScore = actualPlayer.getScore();
            actualPlayerDTOResponse.setScore(updatedPlayerScore);
        }
    }

    public int getPlayerActualScore(Player player, Card playerCard) {
        int previousScore = player.getScore();
        int cardCount = deckService.checkCardCount(playerCard);
        int actualScore = previousScore + cardCount;

        return actualScore;
    }

    public void setActualScore(Player player, Card playerCard) {
        int playerActualScore = getPlayerActualScore(player, playerCard);
        player.setScore(playerActualScore);
    }

    public RoundDTOResponse getRoundResponse(List<PlayerDTOResponse> playerDTOResponses) {
        int countLimit = 21;
        var hasLostGame = playerDTOResponses.stream()
                .filter(player -> player.getScore() > countLimit)
                .findFirst();
        boolean hasLostGamePresent = hasLostGame.isPresent();

        if(hasLostGamePresent) {
            boolean isDealer = hasLostGame.get().isDealer();
            var winner = isDealer ? "player" : "dealer";
            var roundDTOResponse = RoundDTOResponse.builder()
                        .playerDTOResponse(playerDTOResponses)
                        .hasEnded(true)
                        .winner(winner)
                        .build();

                return roundDTOResponse;
            }

            var roundDTOResponse = RoundDTOResponse.builder()
                    .playerDTOResponse(playerDTOResponses)
                    .hasEnded(false)
                    .winner(null)
                    .build();

            return roundDTOResponse;
    }

    public PlayerDTOResponse getPlayerDTOResponses(Player player, Card playerCard, boolean isDealer) {
        var playerResponse = PlayerDTOResponse.builder()
                .score(player.getScore())
                .cards(new ArrayList<>(Collections.singletonList(playerCard)))
                .isDealer(isDealer)
                .build();

        return playerResponse;
    }

}
