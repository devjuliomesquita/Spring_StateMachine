package com.juliomesquita.statemachime.infra.utils;

import com.juliomesquita.statemachime.domain.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PopulateDatabase implements CommandLineRunner {
    private final ProjectService projectService;

    @Override
    public void run(String... args) throws Exception {
        this.projectService.initProject("Projeto A");
        this.projectService.initProject("Projeto B");
        this.projectService.initProject("Projeto C");
    }
}
