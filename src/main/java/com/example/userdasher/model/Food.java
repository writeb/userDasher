package com.example.userdasher.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "t_foods")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private String cafe;

    @ManyToOne
    private User user;
}
