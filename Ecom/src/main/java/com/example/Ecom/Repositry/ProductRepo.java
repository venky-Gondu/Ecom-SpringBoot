package com.example.Ecom.Repositry;

import com.example.Ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Products, Integer> {
    //JPQL is the JPA (Java Persistence API) query Language
    @Query("SELECT p FROM Products p WHERE " +
            "LOWER(p.name) LIKE LOWER(:keyword) OR " +
            "LOWER(p.description) LIKE LOWER(:keyword) OR " +
            "LOWER(p.brand) LIKE LOWER(:keyword) OR " +
            "LOWER(p.category) LIKE LOWER(:keyword)")
    List<Products> search(@Param("keyword") String keyword);


}

