package com.juliomesquita.statemachime.infra.services;

import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.domain.interfaces.SendEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
class SendEventImpl implements SendEvent {
    private static final String PROJECT_ID_HEADER = "project_id";

    @Override
    public void sendMessage(UUID projectId, StateMachine<StatesType, EventsType> sm, EventsType event) {
        Message<EventsType> msg = MessageBuilder.withPayload(event)
                .setHeader(PROJECT_ID_HEADER, projectId)
                .build();
        sm.sendEvent(Mono.just(msg))
                .subscribe(result -> System.out.println(result.getResultType()));
    }
}
