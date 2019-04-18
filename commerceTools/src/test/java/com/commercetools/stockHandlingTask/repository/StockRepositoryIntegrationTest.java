package com.commercetools.stockHandlingTask.repository;

import com.commercetools.stockHandlingTask.entities.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void findByProductId() {

        // given
        Stock testStock = new Stock("TestStock",10,"Veg-Test",new Date());

        entityManager.persist(testStock);
        entityManager.flush();

        // when
        List<Stock> foundStock = stockRepository.findByProductId("Veg-Test");

        // then
        assertEquals(foundStock.get(0).getId(),testStock.getId());
    }
}