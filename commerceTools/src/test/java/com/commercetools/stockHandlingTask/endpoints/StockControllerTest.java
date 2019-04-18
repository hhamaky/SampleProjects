package com.commercetools.stockHandlingTask.endpoints;

import com.commercetools.stockHandlingTask.dto.BaseStockDTO;
import com.commercetools.stockHandlingTask.dto.ProductStockDTO;
import com.commercetools.stockHandlingTask.dto.StockDTO;
import com.commercetools.stockHandlingTask.exception.OutdatedStockRequestException;
import com.commercetools.stockHandlingTask.exception.ProductNotFoundException;
import com.commercetools.stockHandlingTask.exception.StockEntryExistsException;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import com.commercetools.stockHandlingTask.service.impl.StockServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StockServiceImpl stockService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void updateStock() throws Exception {
        StockDTO stockDTO = new StockDTO("3","Iphone10",new Date(),Integer.valueOf(10));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(stockDTO);

        given(stockService.updateStock(any())).willReturn(stockDTO);
        mvc.perform(post("/updateStock")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
        verify(stockService, VerificationModeFactory.times(1)).updateStock(any());
        reset(stockService);
    }

    @Test
    public void updateOutdatedStock() throws Exception {

        StockDTO stockDTO = new StockDTO("3","Iphone10",new Date(),Integer.valueOf(10));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(stockDTO);

        given(stockService.updateStock(any())).willThrow(OutdatedStockRequestException.class);
        mvc.perform(post("/updateStock")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
        verify(stockService, VerificationModeFactory.times(1)).updateStock(any());
        reset(stockService);
    }

    @Test
    public void updateExistingStock() throws Exception {

        StockDTO stockDTO = new StockDTO("3","Iphone10",new Date(),Integer.valueOf(10));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(stockDTO);

        given(stockService.updateStock(any())).willThrow(StockEntryExistsException.class);
        mvc.perform(post("/updateStock")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
        verify(stockService, VerificationModeFactory.times(1)).updateStock(any());
        reset(stockService);
    }

    @Test
    public void getCurrentStock() throws Exception {
        ProductStockDTO productStockDTO = new ProductStockDTO("Iphone8",new Date(),new BaseStockDTO("10",new Date(),10));
        given(stockService.getCurrentStock(any(),any())).willReturn(productStockDTO);

        mvc.perform(get("/stock?productId=Iphone8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productStockDTO.getProductId())));

        verify(stockService, VerificationModeFactory.times(1)).getCurrentStock(any(),any());
        reset(stockService);
    }

    @Test
    public void getCurrentStockWithWrongProdcutId() throws Exception {
        given(stockService.getCurrentStock(any(),any())).willThrow(ProductNotFoundException.class);

        mvc.perform(get("/stock?productId=Iphone100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(stockService, VerificationModeFactory.times(1)).getCurrentStock(any(),any());
        reset(stockService);
    }


}