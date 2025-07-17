package com.reaksa.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "product_id")
    private Long productId;

    private Long quantity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
