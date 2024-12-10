package com.abn.emsdata.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "customerSeqGenerator")
    @SequenceGenerator(name = "customerSeqGenerator", sequenceName = "customer_sequence", allocationSize = 1)
    private Long id;
    @Column
    private String firstName;
    @Column
    private String surName;

    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    private List<Project> projects =new ArrayList<>();

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}
