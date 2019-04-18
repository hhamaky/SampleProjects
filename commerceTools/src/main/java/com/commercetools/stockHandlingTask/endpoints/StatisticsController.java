package com.commercetools.stockHandlingTask.endpoints;

import com.commercetools.stockHandlingTask.dto.StatisticsDTO;
import com.commercetools.stockHandlingTask.service.impl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsServiceImpl statisticsService;

    @GetMapping(path = "/statistics")
    public StatisticsDTO getStatistics(@RequestParam("time") String range) {
        return statisticsService.getStatistics(range);
    }
}
