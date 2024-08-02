package com.juliomesquita.statemachime.infra.services;

import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.domain.interfaces.RecoveryState;
import com.juliomesquita.statemachime.infra.persistence.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class RecoveryStateImpl implements RecoveryState {
    private final ProjectRepository projectRepository;
    private final StateMachineFactory<StatesType, EventsType> stateMachineFactory;

    @Override
    public StateMachine<StatesType, EventsType> recovery(UUID machineId) {
        Project project = this.projectRepository.findById(machineId)
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada."));
        StateMachine<StatesType, EventsType> sm = stateMachineFactory.getStateMachine(machineId);
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
}
