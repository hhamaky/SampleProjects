package com.commercetools.stockHandlingTask.service;

import com.commercetools.stockHandlingTask.dto.StatisticsDTO;

public interface StatisticsService {
    public StatisticsDTO getStatistics(String range);
}
