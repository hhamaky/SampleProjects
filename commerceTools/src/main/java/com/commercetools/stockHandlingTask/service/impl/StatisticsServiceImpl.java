package com.commercetools.stockHandlingTask.service.impl;

import com.commercetools.stockHandlingTask.exception.InvalidRangeException;
import com.commercetools.stockHandlingTask.service.StatisticsService;
import com.commercetools.stockHandlingTask.util.MapperUtil;
import com.commercetools.stockHandlingTask.dto.SoldProductDTO;
import com.commercetools.stockHandlingTask.dto.StatisticsDTO;
import com.commercetools.stockHandlingTask.dto.StockDTO;
import com.commercetools.stockHandlingTask.entities.Stock;
import com.commercetools.stockHandlingTask.enums.RangeEnum;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Transactional
    public StatisticsDTO getStatistics(String range) {
        validateRange(range);
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        Date requestTimestamp = new Date();
        statisticsDTO.setRequestTimestamp(requestTimestamp);
        statisticsDTO.setRange(range);
        statisticsDTO.setTopSellingProducts(getTopSelling(range,requestTimestamp));
        statisticsDTO.setTopAvailableProducts(getTopAvailable(requestTimestamp));
        return statisticsDTO;
    }

    private void validateRange(String range) {
        if (!range.equals(RangeEnum.today.name()) && !range.equals(RangeEnum.lastMonth.name())) {
            final String message = "Range:{" + range + "} is not supported, values {today} or {lastMonth} are only supported.";
            log.error(message);
            throw new InvalidRangeException(message);
        }
    }

    private List<StockDTO> getTopAvailable(Date requestTimestamp) {
        List<Stock> allRecords = stockRepository.findAll(); //operation separated in 2 line for the sake of unit test mocking.
        List<StockDTO> stocksDTO = allRecords
                .stream()
                .filter(s -> s.getTimestamp().before(requestTimestamp))
                .sorted((s1, s2) -> s2.getQuantity().compareTo(s1.getQuantity()))
                .limit(3)
                .map(e -> MapperUtil.mapObject(e, StockDTO.class))
                .collect(Collectors.toList());

        return stocksDTO;
    }

    private List<SoldProductDTO> getTopSelling(String range,Date requestTimestamp) {

        if (range.equals(RangeEnum.today.name())) {
            Date today = Date.from(LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant());
            List<SoldProductDTO> topSelling = calculateTopSelling(requestTimestamp, today);
            return getMaxTopSelling(topSelling);


        } else if (range.equals(RangeEnum.lastMonth.name())) {
            Date lastMonth = Date.from(LocalDate.now().minusDays(30).atStartOfDay(ZoneOffset.UTC).toInstant());
            List<SoldProductDTO> topSelling = calculateTopSelling(requestTimestamp, lastMonth);
            return getMaxTopSelling(topSelling);
        }
        return null;
    }

    private List<SoldProductDTO> getMaxTopSelling(List<SoldProductDTO> topSelling) {
        return topSelling.stream()
                .sorted((e1, e2) -> e2.getItemsSold()
                        .compareTo(e1.getItemsSold()))
                        .limit(3)
                        .collect(Collectors.toList());
    }

    private List<SoldProductDTO> calculateTopSelling(Date requestTimestamp, Date today) {
        List<SoldProductDTO> topSelling = new ArrayList<SoldProductDTO>();
        List<Stock> allRecords = stockRepository.findAll(); //operation separated in 2 line for the sake of unit test mocking.
        Map<String, List<Stock>> mapOfProducts = allRecords
                .stream()
                .filter(s -> s.getTimestamp().before(requestTimestamp))
                .sorted((s1, s2) -> s2
                        .getTimestamp()
                        .compareTo(s1
                                .getTimestamp()))
                .collect(Collectors.groupingBy(i -> i.getProductId(), Collectors.toList()));

        for (Map.Entry<String,List<Stock>> entry:mapOfProducts.entrySet()
             ) {
            int result = 0;
            SoldProductDTO soldProductDTO = new SoldProductDTO();
            soldProductDTO.setProductId(entry.getKey());
            for (int i = 0; i < entry.getValue().size() - 1 ; i++) {

               if(entry.getValue().get(i).getTimestamp().before(today)){
                   break;
               }
               if(entry.getValue().get(i).getQuantity() < entry.getValue().get(i+1).getQuantity()){
                   result += entry.getValue().get(i+1).getQuantity() - entry.getValue().get(i).getQuantity();
               }
            }
            soldProductDTO.setItemsSold(result);

            topSelling.add(soldProductDTO);

        }
        return topSelling;
    }


}
