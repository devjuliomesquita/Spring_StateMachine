package com.juliomesquita.statemachime.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_budgets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "budget_id")
    private UUID id;

    @Column(name = "budget_value")
    private BigDecimal value;

    @OneToOne(mappedBy = "budget")
    private Project project;
    /*
    * sd;klfj;sasdasdas*/
}
