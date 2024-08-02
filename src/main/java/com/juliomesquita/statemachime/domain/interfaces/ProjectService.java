package com.juliomesquita.statemachime.domain.interfaces;

import com.juliomesquita.statemachime.domain.entities.Project;

import java.util.UUID;

public interface ProjectService {
    Project initProject(String name);
    Project inProgressProject(UUID projectId);
    Project userReviewProject(UUID projectId);
    Project approvedProject(UUID projectId);
    Project completedProject(UUID projectId);
    Project canceledProject(UUID projectId);
    Project reopenProject(UUID projectId);
}
