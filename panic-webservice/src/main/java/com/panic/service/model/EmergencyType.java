package com.panic.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by tabs on 7/23/16.
 */
@Entity
@Table(name = "emergency_types")
public class EmergencyType {
    private Long id;
    private String name;
    private Collection<Consideration> considerations;
    private Collection<CustomField> customFields;

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
    @Column(name = "name", length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "emergencyType")
    public Collection<Consideration> getConsiderations() {
        return considerations;
    }

    public void setConsiderations(Collection<Consideration> considerations) {
        this.considerations = considerations;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "emergencyType")
    public Collection<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Collection<CustomField> customFields) {
        this.customFields = customFields;
    }
}
