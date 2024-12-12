package com.abn.emsdata.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customerSeqGenerator")
    @SequenceGenerator(name = "customerSeqGenerator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;
    @Column
    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<Employee> employee;
}
