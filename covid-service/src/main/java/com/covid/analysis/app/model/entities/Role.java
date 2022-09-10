package com.covid.analysis.app.model.entities;


import com.covid.analysis.app.model.common.AbstractEntity;
import com.covid.analysis.app.model.enums.ERole;;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "role")
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}