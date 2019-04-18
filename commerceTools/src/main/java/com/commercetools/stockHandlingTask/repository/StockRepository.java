package com.commercetools.stockHandlingTask.repository;

import com.commercetools.stockHandlingTask.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock,String> {

    @Query("FROM Stock s where s.productId = :id")
    List<Stock> findByProductId(@Param("id") String id);
}
