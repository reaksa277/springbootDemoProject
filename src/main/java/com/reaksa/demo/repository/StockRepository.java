package com.reaksa.demo.repository;

import com.reaksa.demo.entity.Stock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
    List<Stock> findByProductIdIn(List<Long> productIds, Sort createdAt);
}
