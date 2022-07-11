package com.example.testtask.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "attempts")
@Data
@Accessors(chain = true)
public class Attempts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String numberSession;
    private Long time;
    private Integer countAttempts;
}
