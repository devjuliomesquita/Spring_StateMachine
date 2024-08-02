package com.juliomesquita.statemachime.domain.dtos;

import com.juliomesquita.statemachime.domain.enums.ProjectType;
import lombok.Builder;

@Builder
public record ProjectDTO(String name, ProjectType type) {
}
