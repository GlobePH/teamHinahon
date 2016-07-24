package com.panic.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by tabs on 7/23/16.
 */
@Entity
@Table(name = "considerations")
public class Consideration {
    private Long id;
    private Long emergencyTypeId;
    private String name;
    private EmergencyType emergencyType;
    private Collection<Resource> resources;

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

    @Basic
    @Column(name = "e_type_id", updatable = false, insertable = false)
    public Long getEmergencyTypeId() {
        return emergencyTypeId;
    }

    public void setEmergencyTypeId(Long emergencyTypeId) {
        this.emergencyTypeId = emergencyTypeId;
    }

    @ManyToOne
    @NotNull
    @JsonIgnore
    @JoinColumn(name = "e_type_id", referencedColumnName = "id")
    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public void setEmergencyType(EmergencyType emergencyType) {
        this.emergencyType = emergencyType;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "consideration")
    public Collection<Resource> getResources() {
        return resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }
}
