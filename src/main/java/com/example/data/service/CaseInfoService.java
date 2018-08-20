package com.example.data.service;

import java.math.BigDecimal;

public interface CaseInfoService {
    /**
     *
     * @param batchNumber
     * @param allPayAmount
     * @param personId
     * @param userId
     * @return
     */
    void addCaseInfo(String batchNumber,BigDecimal allPayAmount,String personId,String userId);
}
