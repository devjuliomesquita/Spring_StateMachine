package com.juliomesquita.statemachime.infra.persistence;

import com.juliomesquita.statemachime.domain.dtos.BudgetDTO;
import com.juliomesquita.statemachime.domain.entities.Budget;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapper {
    public Budget toEntity(BudgetDTO budgetDTO){
        return Budget.builder().value(budgetDTO.value()).build();
    }
}
