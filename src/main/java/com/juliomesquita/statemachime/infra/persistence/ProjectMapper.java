package com.juliomesquita.statemachime.infra.persistence;

import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    public Project toEntity(String name){
        return Project.builder().name(name).state(StatesType.PLANNED).build();
    }
}
