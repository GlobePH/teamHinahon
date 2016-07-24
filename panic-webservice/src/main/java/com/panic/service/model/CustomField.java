package com.panic.service.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tabs on 7/23/16.
 */
@Entity
@Table(name = "custom_fields")
public class CustomField {
    private Long id;
    private String name;
    private EmergencyType emergencyType;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @NotNull
    @Column(name = "name", length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @NotNull
    @JoinColumn(name = "e_type_id", referencedColumnName = "id")
    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(EmergencyType emergencyType) {
        this.emergencyType = emergencyType;
    }
}
