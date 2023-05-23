package com.algeujunior.altjack.domain.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoundDTOResponse {

    private List<PlayerDTOResponse> playerDTOResponse;
    private String winner;
    private boolean ended;
}
