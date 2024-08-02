package com.juliomesquita.statemachime.infra.services;

import com.juliomesquita.statemachime.domain.dtos.BudgetDTO;
import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.domain.interfaces.SendEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Component
class SendEventImpl implements SendEvent {
    private static final String PROJECT_ID_HEADER = "project_id";
    private static final String EVENT_DATA = "data";

    @Override
    public Object sendMessage(Object object, StateMachine<StatesType, EventsType> sm, EventsType event) {
        CompletableFuture<Object> futureResult = new CompletableFuture<>();
        if(object instanceof BudgetDTO b){
            Message<EventsType> msg = MessageBuilder.withPayload(event)
                    .setHeader(PROJECT_ID_HEADER, b.projectId())
                    .setHeader(EVENT_DATA, b.value())
                    .build();
            sm.sendEvent(Mono.just(msg))
                    .subscribe(result -> {
                        Object project = sm.getExtendedState().getVariables().get("project");
                        futureResult.complete(project);
                    }, futureResult::completeExceptionally);
        }
        return futureResult.join();
    }
}
