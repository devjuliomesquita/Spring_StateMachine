package com.juliomesquita.statemachime.infra.factory.statemachine.beans_config;

import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListener;

public interface ProjectManagementSMBeansConfig {
    StateMachineListener<StatesType, EventsType> eventListener();
    Action<StatesType, EventsType> inProgressAction();
    Guard<StatesType, EventsType> validateInProgressGuard();
}
