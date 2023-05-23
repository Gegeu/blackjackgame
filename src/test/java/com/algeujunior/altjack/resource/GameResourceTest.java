package com.algeujunior.altjack.resource;

import com.algeujunior.altjack.domain.Card;
import com.algeujunior.altjack.domain.Score;
import com.algeujunior.altjack.domain.dto.request.ScoreDTORequest;
import com.algeujunior.altjack.domain.dto.response.PlayerDTOResponse;
import com.algeujunior.altjack.domain.dto.response.RoundDTOResponse;
import com.algeujunior.altjack.domain.enums.Rank;
import com.algeujunior.altjack.domain.enums.Suit;
import com.algeujunior.altjack.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GameResource.class)
@AutoConfigureMockMvc
public class GameResourceTest {

    String API = "/v1/games";

    @MockBean
    GameService gameService;
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Should create a game")
    public void createGame() throws Exception {
        var gameId = "1";
        var request = MockMvcRequestBuilders.post(API);

        BDDMockito.given(gameService.initNewGame()).willReturn(gameId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    @DisplayName("Should play a round")
    public void playRound() throws Exception {
        var gameId = "1";
        var request = MockMvcRequestBuilders.patch(API + "/" + gameId);
        var roundDTOResponse = roundDTOResponseMock();

        BDDMockito.given(gameService.playRound(gameId)).willReturn(roundDTOResponse);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("playerDTOResponse", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("ended").value(false));
    }

    @Test
    @DisplayName("Should finish a game")
    public void finishGame() throws Exception {
        var gameId = "1";
        var request = MockMvcRequestBuilders.delete(API + "/" + gameId);

        BDDMockito.doNothing().when(gameService).finishGame(gameId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Should save a score")
    public void saveScore() throws Exception {
        var gameId = "1";
        var scoreDTORequest = scoreDTORequestMock();
        String json = new ObjectMapper().writeValueAsString(scoreDTORequest);

        var request = MockMvcRequestBuilders
                .post(API + "/" + gameId + "/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        BDDMockito.doNothing().when(gameService).savePlayerResult(scoreDTORequest ,gameId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Should get score results")
    public void getScoreResults() throws Exception {
        var score = scoreMock();
        var request = MockMvcRequestBuilders.get(API + "/results");

        BDDMockito.given(gameService.getResultScores()).willReturn(Collections.singletonList(score));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    private Score scoreMock() {
        return Score.builder()
                .id(1L)
                .playerName("Player 1")
                .score(10)
                .dateTime(LocalDateTime.now())
                .build();
    }

    private ScoreDTORequest scoreDTORequestMock() {
        return ScoreDTORequest.builder()
                .playerName("Player 2")
                .build();
    }

    private RoundDTOResponse roundDTOResponseMock() {
        return RoundDTOResponse.builder()
                .playerDTOResponse(Collections.singletonList(playerDTOResponseMock()))
                .ended(false)
                .winner("")
                .build();
    }

    private PlayerDTOResponse playerDTOResponseMock() {
        return PlayerDTOResponse.builder()
                .cards(cardsListMock())
                .score(10)
                .isDealer(false)
                .build();
    }

    private List<Card> cardsListMock() {
        Card cardOne = new Card(Suit.CLUBS, Rank.ACE);
        Card carTwo = new Card(Suit.HEARTS, Rank.EIGHT);
        var cards = new ArrayList<>(Arrays.asList(cardOne, carTwo));

        return cards;
    }

}
