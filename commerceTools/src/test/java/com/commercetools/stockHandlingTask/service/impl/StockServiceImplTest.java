package com.commercetools.stockHandlingTask.service.impl;

import com.commercetools.stockHandlingTask.dto.ProductStockDTO;
import com.commercetools.stockHandlingTask.dto.StockDTO;
import com.commercetools.stockHandlingTask.entities.Stock;
import com.commercetools.stockHandlingTask.exception.OutdatedStockRequestException;
import com.commercetools.stockHandlingTask.exception.ProductNotFoundException;
import com.commercetools.stockHandlingTask.exception.StockEntryExistsException;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import com.commercetools.stockHandlingTask.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;

@RunWith(SpringRunner.class)
public class StockServiceImplTest {

    @TestConfiguration
    static class StockServiceImplTestContextConfiguration {

        @Bean
        public StockService stockService() {
            return new StockServiceImpl();
        }
    }

    @Autowired
    private StockService stockServiceImpl;

    @MockBean
    private StockRepository stockRepository;


    private void SetupInitialData(Date todayMorning) {
        String productId = "Veg-Test";
        Stock testStock1 = new Stock("TestStock", 10, productId, todayMorning);
        Stock testStock2 = new Stock("TestStock2", 30, productId, todayMorning);

        List<Stock> testStockList = new ArrayList<Stock>();
        testStockList.add(testStock1);
        testStockList.add(testStock2);

        Mockito.when(stockRepository.findByProductId(productId))
                .thenReturn(testStockList);
    }

    @Test
    public void getCurrentStock() {

        String productId = "Veg-Test";
        Date todayMorning = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        SetupInitialData(todayMorning);
        ProductStockDTO productStockDTO = stockServiceImpl.getCurrentStock(productId, new Date());

        assertEquals(productStockDTO.getStock().getQuantity(),Integer.valueOf(10) );

    }

    @Test(expected = ProductNotFoundException.class)
    public void getCurrentStockWithNoStockFound() {

        String productId = "Veg-Test-NoStock";
        stockServiceImpl.getCurrentStock(productId, new Date());
    }

    @Test
    public void updateStock() {
        String productId = "Veg-Test";
        Stock testStock3 = new Stock("TestStock3", 20, productId, new Date());
        Mockito.when(stockRepository.save(any()))
                .thenReturn(testStock3);


        StockDTO stockDTO = new StockDTO("TestStock3",productId, new Date(),20);
        StockDTO stockDtoPersisted = stockServiceImpl.updateStock(stockDTO);
        assertEquals(stockDtoPersisted.getId(),"TestStock3" );
    }

    @Test(expected = OutdatedStockRequestException.class)
    public void updateOutdatedStock() {
        String productId = "Veg-Test";
        SetupInitialData(new Date());
        Date todayMorning = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        StockDTO stockDTO = new StockDTO("TestStock4",productId, todayMorning,40);
        stockServiceImpl.updateStock(stockDTO);

    }

    @Test(expected = StockEntryExistsException.class)
    public void updateAlreadyExistingStock() {
        String productId = "Veg-Test";
        Mockito.when(stockRepository.save(any()))
                .thenThrow(new DataIntegrityViolationException("Already existing stock id"));

        StockDTO stockDTO = new StockDTO("TestStock2",productId, new Date(),40);
        stockServiceImpl.updateStock(stockDTO);

    }
}