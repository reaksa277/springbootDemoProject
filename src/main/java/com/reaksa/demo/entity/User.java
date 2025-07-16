package com.reaksa.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data  // include setter and getter
@Entity
@Table(name = "users") // table name in db
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String email;
    private String role;
    @Column(name = "create_at")
    private LocalDateTime createAt;
}
