package com.commercetools.stockHandlingTask.service.impl;

import com.commercetools.stockHandlingTask.exception.StockEntryExistsException;
import com.commercetools.stockHandlingTask.service.StockService;
import com.commercetools.stockHandlingTask.util.MapperUtil;
import com.commercetools.stockHandlingTask.dto.*;
import com.commercetools.stockHandlingTask.entities.Stock;
import com.commercetools.stockHandlingTask.exception.OutdatedStockRequestException;
import com.commercetools.stockHandlingTask.exception.ProductNotFoundException;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    public ProductStockDTO getCurrentStock(String productId, Date requestTimestamp) {

        List<Stock> stock = stockRepository.findByProductId(productId);

        Optional<Stock> first = stock
                .stream()
                .filter(d -> d.getTimestamp().before(requestTimestamp))
                .sorted((s1, s2) -> s2
                        .getTimestamp()
                        .compareTo(s1
                                .getTimestamp()))
                .findFirst();

        if (!first.isPresent()) {
            final String message = "Product Id:" + productId + " is Not Found";
            log.error(message);
            throw new ProductNotFoundException(message);
        }

        BaseStockDTO baseStockDTO = MapperUtil.mapObject(first.get(), BaseStockDTO.class);

        ProductStockDTO productStockDTO = new ProductStockDTO(productId, requestTimestamp, baseStockDTO);
        return productStockDTO;
    }

    @Transactional
    public StockDTO updateStock(StockDTO updateStockDTO) {

        // Map DTO to Entity
        Stock stockEntity = MapperUtil.mapObject(updateStockDTO, Stock.class);


        // validate new timestamp is later the max time stamp for this product id
        List<Stock> stock = stockRepository.findByProductId(updateStockDTO.getProductId());
        long OutdatedStock = stock.stream()
                .filter(d -> d.getTimestamp().after(updateStockDTO.getTimestamp())).count();

        if(OutdatedStock > 0){
            final String message = "Outdated stock update in request with product Id:" + updateStockDTO.getProductId();
            log.error(message);
            throw new OutdatedStockRequestException(message);
        }

        // create new Enty and validate item if doesn't exist.
        Stock newStockEnty;
        try {
            newStockEnty = stockRepository.save(stockEntity);
        } catch (DataIntegrityViolationException e) {
            final String message = "Stock entry already exists in request with Stock Id:" + updateStockDTO.getId();
            log.error(message);
            throw new StockEntryExistsException(message);
        }

        StockDTO stockDTO = MapperUtil.mapObject(newStockEnty, StockDTO.class);

        return stockDTO;
    }

}
