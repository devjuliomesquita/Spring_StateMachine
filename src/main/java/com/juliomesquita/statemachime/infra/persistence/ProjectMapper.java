package com.juliomesquita.statemachime.infra.persistence;

import com.juliomesquita.statemachime.domain.dtos.ProjectDTO;
import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public Project toEntity(ProjectDTO projectDTO){
        return Project.builder()
                .name(projectDTO.name())
                .state(StatesType.PLANNED)
                .type(projectDTO.type())
                .build();
    }
}
