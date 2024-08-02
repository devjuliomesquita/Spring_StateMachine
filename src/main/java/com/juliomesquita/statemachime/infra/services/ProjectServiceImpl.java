package com.juliomesquita.statemachime.infra.services;

import com.juliomesquita.statemachime.domain.dtos.BudgetDTO;
import com.juliomesquita.statemachime.domain.dtos.ProjectDTO;
import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.domain.interfaces.ProjectService;
import com.juliomesquita.statemachime.domain.interfaces.RecoveryState;
import com.juliomesquita.statemachime.domain.interfaces.SendEvent;
import com.juliomesquita.statemachime.infra.persistence.ProjectMapper;
import com.juliomesquita.statemachime.infra.persistence.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final RecoveryState recoveryState;
    private final SendEvent sendEvent;

    @Transactional
    @Override
    public Project initProject(ProjectDTO projectDTO) {
        Project entity = this.projectMapper.toEntity(projectDTO);
        return this.projectRepository.save(entity);
    }

    @Transactional
    @Override
    public Project inProgressProject(BudgetDTO budgetDTO) {
        StateMachine<StatesType, EventsType> sm = this.recoveryState.recovery(budgetDTO.projectId());
        this.sendEvent.sendMessage(budgetDTO.projectId(), sm, EventsType.PLANNED_IN_PROGRESS);
        return this.projectRepository.findById(budgetDTO.projectId()).orElseThrow();
    }

    @Override
    public Project userReviewProject(UUID projectId) {
        return null;
    }

    @Override
    public Project approvedProject(UUID projectId) {
        return null;
    }

    @Override
    public Project completedProject(UUID projectId) {
        return null;
    }

    @Override
    public Project canceledProject(UUID projectId) {
        return null;
    }

    @Override
    public Project reopenProject(UUID projectId) {
        return null;
    }
}
