package com.commercetools.stockHandlingTask.endpoints;

import com.commercetools.stockHandlingTask.dto.StatisticsDTO;
import com.commercetools.stockHandlingTask.exception.InvalidRangeException;
import com.commercetools.stockHandlingTask.repository.StockRepository;
import com.commercetools.stockHandlingTask.service.impl.StatisticsServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsServiceImpl statisticsService;

    @MockBean
    private StockRepository stockRepository;

    @Test
    public void getStatistics() throws Exception {
        StatisticsDTO statisticsDTO = new StatisticsDTO();
        given(statisticsService.getStatistics(any())).willReturn(statisticsDTO);

        mvc.perform(get("/statistics?time=lastMonth")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.range", is(statisticsDTO.getRange())));

        verify(statisticsService, VerificationModeFactory.times(1)).getStatistics(any());
        reset(statisticsService);
    }

    @Test
    public void getStatisticsWithWrongRange() throws Exception {
        given(statisticsService.getStatistics(any())).willThrow(InvalidRangeException.class);

        mvc.perform(get("/statistics?time=lastMonth")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(statisticsService, VerificationModeFactory.times(1)).getStatistics(any());
        reset(statisticsService);
    }

    @Test
    public void getStatisticsWithAnyOtherError() throws Exception {
        given(statisticsService.getStatistics(any())).willThrow(NullPointerException.class);

        mvc.perform(get("/statistics?time=lastMonth")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(statisticsService, VerificationModeFactory.times(1)).getStatistics(any());
        reset(statisticsService);
    }
}