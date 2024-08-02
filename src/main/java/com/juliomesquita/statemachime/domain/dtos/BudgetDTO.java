package com.juliomesquita.statemachime.domain.dtos;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record BudgetDTO(UUID projectId, BigDecimal value) {
}
