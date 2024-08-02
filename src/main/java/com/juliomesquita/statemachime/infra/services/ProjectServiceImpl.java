package com.juliomesquita.statemachime.infra.services;

import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.domain.interfaces.ProjectService;
import com.juliomesquita.statemachime.infra.persistence.ProjectMapper;
import com.juliomesquita.statemachime.infra.persistence.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    public static final String PROJECT_ID_HEADER = "project_id";

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final StateMachineFactory<StatesType, EventsType> stateMachineFactory;

    @Transactional
    @Override
    public Project initProject(String name) {
        Project entity = this.projectMapper.toEntity(name);
        return this.projectRepository.save(entity);
    }

//    @Transactional
    @Override
    public Project inProgressProject(UUID projectId) {
        StateMachine<StatesType, EventsType> sm = this.recovery(projectId);
        this.sendEvent(projectId, sm, EventsType.PLANNED_IN_PROGRESS);
        return this.projectRepository.findById(projectId).orElseThrow();
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

    private StateMachine<StatesType, EventsType> recovery(UUID projectId){
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada."));
        StateMachine<StatesType, EventsType> sm = stateMachineFactory.getStateMachine(projectId);
        sm.stopReactively().subscribe();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.resetStateMachineReactively(new DefaultStateMachineContext<>(
                            project.getState(),
                            null,
                            null,
                            null))
                            .subscribe();
                });
        sm.startReactively().subscribe();
        return sm;
    }

    private void sendEvent(UUID projectId, StateMachine<StatesType, EventsType> sm, EventsType event){
        Message<EventsType> msg = MessageBuilder.withPayload(event)
                .setHeader(PROJECT_ID_HEADER, projectId)
                .build();
        sm.sendEvent(Mono.just(msg))
                .subscribe(result -> System.out.println(result.getResultType()));
    }
}
