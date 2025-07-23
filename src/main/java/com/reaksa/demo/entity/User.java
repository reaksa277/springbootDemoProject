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
    private String password;
    private String address;
    private String email;
    private String role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
