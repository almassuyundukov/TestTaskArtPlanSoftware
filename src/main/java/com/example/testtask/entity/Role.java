package com.example.testtask.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_role")
@Data
@Accessors(chain = true)
public class Role implements GrantedAuthority {

    @Id
    private Integer id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }


}
