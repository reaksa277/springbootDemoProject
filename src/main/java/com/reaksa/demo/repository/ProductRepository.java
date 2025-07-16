package com.reaksa.demo.repository;

import com.reaksa.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE %:name%")
    List<Product> findProductsWithFilters(@Param("name") String name);
}
