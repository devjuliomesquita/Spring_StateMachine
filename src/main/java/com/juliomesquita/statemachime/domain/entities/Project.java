package com.juliomesquita.statemachime.domain.entities;

import com.juliomesquita.statemachime.domain.enums.ProjectType;
import com.juliomesquita.statemachime.domain.enums.StatesType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tb_projects")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "project_id")
    private UUID id;

    @Column(name = "project_name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_state")
    private StatesType state;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type")
    private ProjectType type;

    @OneToOne(mappedBy = "project")
    @JoinColumn(name = "project_budget_id", referencedColumnName = "budget_id")
    private Budget budget;

}
