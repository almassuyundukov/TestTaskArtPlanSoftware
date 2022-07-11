package com.example.testtask.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "pet")
@Data
@Accessors(chain = true)
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String species;
    private Date dateOfBirth;
    private Character character;
    private String nickname;
    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "pet-user")
    private User user;

}
