package com.juliomesquita.statemachime.infra.controllers;

import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping(value = "/{projectId}")
    public ResponseEntity<Project> inProgressProject(@PathVariable("projectId") UUID projectId){
        return ResponseEntity.ok(this.projectService.inProgressProject(projectId));
    }
}
