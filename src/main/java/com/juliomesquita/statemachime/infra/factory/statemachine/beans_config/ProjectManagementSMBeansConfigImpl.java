package com.juliomesquita.statemachime.infra.factory.statemachine.beans_config;

import com.juliomesquita.statemachime.domain.entities.Project;
import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.infra.persistence.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
class ProjectManagementSMBeansConfigImpl implements ProjectManagementSMBeansConfig {
    private final ProjectRepository projectRepository;

    @Bean
    @Override
    public StateMachineListener<StatesType, EventsType> eventListener() {
        return new StateMachineListenerAdapter<StatesType, EventsType>() {
            @Override
            public void stateChanged(State<StatesType, EventsType> from, State<StatesType, EventsType> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    @Bean
    @Override
    public Action<StatesType, EventsType> inProgressAction() {
        return stateContext -> {
            UUID projectId = (UUID) stateContext.getMessageHeader("project_id");
            Project project = this.projectRepository.findById(projectId)
                    .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada."));
            project.setState(StatesType.IN_PROGRESS);
            this.projectRepository.save(project);
            System.out.println("Projeto Atualizado.");
        };
    }

    @Bean
    @Override
    public Guard<StatesType, EventsType> validateInProgressGuard() {
        return stateContext -> {
            UUID projectId = (UUID) stateContext.getMessageHeader("project_id");
            if(projectId == null){
                System.out.println("Project Id não enviado.");
            }
            System.out.println("Projeto Validado.");
            return true;
        };
    }
}
