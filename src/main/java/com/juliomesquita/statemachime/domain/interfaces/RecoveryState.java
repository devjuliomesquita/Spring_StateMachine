package com.juliomesquita.statemachime.domain.interfaces;

import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

public interface RecoveryState {
    StateMachine<StatesType, EventsType> recovery(UUID machineId);
}
