package com.abn.emsdata.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customerSeqGenerator")
    @SequenceGenerator(name = "customerSeqGenerator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;
    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;
}
