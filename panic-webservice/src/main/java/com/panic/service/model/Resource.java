package com.panic.service.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by tabs on 7/23/16.
 */
@Entity
@Table(name = "resources")
public class Resource {
    private Long id;
    private String name;
    private Consideration consideration;

    @Id
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
    @JoinColumn(name = "consideration_id", referencedColumnName = "id")
    public Consideration getConsideration() {
        return consideration;
    }

    public void setConsideration(Consideration consideration) {
        this.consideration = consideration;
    }
}
