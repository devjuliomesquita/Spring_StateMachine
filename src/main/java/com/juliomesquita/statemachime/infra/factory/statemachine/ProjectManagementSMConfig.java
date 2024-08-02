package com.juliomesquita.statemachime.infra.factory.statemachine;

import com.juliomesquita.statemachime.domain.enums.EventsType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import com.juliomesquita.statemachime.infra.factory.statemachine.beans_config.ProjectManagementSMBeansConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class ProjectManagementSMConfig
        extends EnumStateMachineConfigurerAdapter<StatesType, EventsType> {
    private final ProjectManagementSMBeansConfig beansConfig;

    @Override
    public void configure(StateMachineConfigurationConfigurer<StatesType, EventsType> config) throws Exception {
        config.withConfiguration().autoStartup(true).listener(this.beansConfig.eventListener());

    }

    @Override
    public void configure(StateMachineStateConfigurer<StatesType, EventsType> states) throws Exception {
        states.withStates()
                .initial(StatesType.PLANNED)
                .states(EnumSet.allOf(StatesType.class))
                .end(StatesType.CANCELED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StatesType, EventsType> transitions) throws Exception {
        transitions
                .withExternal().source(StatesType.PLANNED).target(StatesType.IN_PROGRESS).event(EventsType.PLANNED_IN_PROGRESS)
                .guard(this.beansConfig.validateInProgressGuard())
                .action(this.beansConfig.inProgressAction())
                .and()
                .withExternal().source(StatesType.IN_PROGRESS).target(StatesType.UNDER_REVIEW).event(EventsType.IN_PROGRESS_UNDER_REVIEW).and()
                .withExternal().source(StatesType.UNDER_REVIEW).target(StatesType.APPROVED).event(EventsType.UNDER_REVIEW_APPROVED).and()
                .withExternal().source(StatesType.UNDER_REVIEW).target(StatesType.IN_PROGRESS).event(EventsType.UNDER_REVIEW_IN_PROGRESS).and()
                .withExternal().source(StatesType.APPROVED).target(StatesType.COMPLETED).event(EventsType.APPROVED_COMPLETED).and()
                .withExternal().source(StatesType.IN_PROGRESS).target(StatesType.CANCELED).event(EventsType.IN_PROGRESS_CANCELED).and()
                .withExternal().source(StatesType.UNDER_REVIEW).target(StatesType.CANCELED).event(EventsType.UNDER_REVIEW_CANCELED).and()
                .withExternal().source(StatesType.PLANNED).target(StatesType.CANCELED).event(EventsType.PLANNED_CANCELED).and()
                .withExternal().source(StatesType.APPROVED).target(StatesType.IN_PROGRESS).event(EventsType.APPROVED_IN_PROGRESS).and()
                .withExternal().source(StatesType.COMPLETED).target(StatesType.IN_PROGRESS).event(EventsType.COMPLETED_IN_PROGRESS);
    }

}
