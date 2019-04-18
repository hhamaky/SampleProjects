package com.commercetools.stockHandlingTask.service.impl;

import com.commercetools.stockHandlingTask.dto.StatisticsDTO;
import com.commercetools.stockHandlingTask.entities.Stock;
import com.commercetools.stockHandlingTask.exception.InvalidRangeException;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import com.commercetools.stockHandlingTask.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class StatisticsServiceImplTest {

    @TestConfiguration
    static class StatisticsServiceImplTestContextConfiguration {

        @Bean
        public StatisticsService statisticsService() {
            return new StatisticsServiceImpl();
        }
    }

    @Autowired
    private StatisticsService StatisticsService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void getTopAvailable() {
        SetupTopAvailableData();
        StatisticsDTO statisticsDTO = StatisticsService.getStatistics("today");
        assertEquals(statisticsDTO.getTopAvailableProducts().get(0).productId,"Iphone8");
    }

    @Test(expected = InvalidRangeException.class)
    public void getStatisticsWithInvalidRange() {
        StatisticsDTO statisticsDTO = StatisticsService.getStatistics("lastYear");
    }
    @Test
    public void getTopSellingToday() {
        SetupTopSellingDataToday();
        StatisticsDTO statisticsDTO = StatisticsService.getStatistics("today");
        assertEquals(statisticsDTO.getTopSellingProducts().get(0).getItemsSold(), Integer.valueOf(140));
    }

    @Test
    public void getTopSellingLastMonth() {
        SetupTopSellingDataLastMonth();
        StatisticsDTO statisticsDTO = StatisticsService.getStatistics("lastMonth");
        assertEquals(statisticsDTO.getTopSellingProducts().get(0).getItemsSold(), Integer.valueOf(140));
    }

    private void SetupTopAvailableData() {

        Date todayMorning = Date.from(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant());

        Stock testStock1 = new Stock("TestStock1", 10, "Iphone5", todayMorning);
        Stock testStock2 = new Stock("TestStock2", 30, "Iphone6", todayMorning);
        Stock testStock3 = new Stock("TestStock3", 50, "Iphone7", todayMorning);
        Stock testStock4 = new Stock("TestStock4", 100, "Iphone8", todayMorning);

        List<Stock> testStockList = new ArrayList<Stock>();
        testStockList.add(testStock1);
        testStockList.add(testStock2);
        testStockList.add(testStock3);
        testStockList.add(testStock4);

        Mockito.when(stockRepository.findAll())
                .thenReturn(testStockList);
    }

    private void SetupTopSellingDataToday() {

        Date todayMorning = Date.from(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant());
        Date lastMonth = Date.from(LocalDate.now().minusDays(30).atStartOfDay(ZoneOffset.UTC).toInstant());

        Stock testStock1 = new Stock("TestStock1", 100, "Iphone8", lastMonth);
        Stock testStock2 = new Stock("TestStock2", 30, "Iphone8", todayMorning);
        Stock testStock3 = new Stock("TestStock1", 130, "Iphone6", lastMonth);
        Stock testStock4 = new Stock("TestStock2", 30, "Iphone6", todayMorning);
        Stock testStock5 = new Stock("TestStock1", 150, "Iphone7", lastMonth);
        Stock testStock6 = new Stock("TestStock2", 30, "Iphone7", todayMorning);
        Stock testStock7 = new Stock("TestStock1", 170, "Iphone8plus", lastMonth);
        Stock testStock8 = new Stock("TestStock2", 30, "Iphone8plus", todayMorning);

        List<Stock> testStockList = new ArrayList<Stock>();
        testStockList.add(testStock1);
        testStockList.add(testStock2);
        testStockList.add(testStock3);
        testStockList.add(testStock4);
        testStockList.add(testStock5);
        testStockList.add(testStock6);
        testStockList.add(testStock7);
        testStockList.add(testStock8);


        Mockito.when(stockRepository.findAll())
                .thenReturn(testStockList);
    }

    private void SetupTopSellingDataLastMonth() {

        Date todayMorning = Date.from(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant());
        Date oldDate = Date.from(LocalDate.now().minusDays(40).atStartOfDay(ZoneOffset.UTC).toInstant());

        Stock testStock1 = new Stock("TestStock1", 100, "Iphone8", oldDate);
        Stock testStock2 = new Stock("TestStock2", 30, "Iphone8", todayMorning);
        Stock testStock3 = new Stock("TestStock1", 130, "Iphone6", oldDate);
        Stock testStock4 = new Stock("TestStock2", 30, "Iphone6", todayMorning);
        Stock testStock5 = new Stock("TestStock1", 150, "Iphone7", oldDate);
        Stock testStock6 = new Stock("TestStock2", 30, "Iphone7", todayMorning);
        Stock testStock7 = new Stock("TestStock1", 170, "Iphone8plus", oldDate);
        Stock testStock8 = new Stock("TestStock2", 30, "Iphone8plus", todayMorning);

        List<Stock> testStockList = new ArrayList<Stock>();
        testStockList.add(testStock1);
        testStockList.add(testStock2);
        testStockList.add(testStock3);
        testStockList.add(testStock4);
        testStockList.add(testStock5);
        testStockList.add(testStock6);
        testStockList.add(testStock7);
        testStockList.add(testStock8);


        Mockito.when(stockRepository.findAll())
                .thenReturn(testStockList);
    }
}





