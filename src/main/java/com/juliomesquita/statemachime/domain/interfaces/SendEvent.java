package com.juliomesquita.statemachime.domain.interfaces;

import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import org.springframework.statemachine.StateMachine;

public interface SendEvent {
    Object sendMessage(Object object, StateMachine<StatesType, EventsType> sm, EventsType event);
}
