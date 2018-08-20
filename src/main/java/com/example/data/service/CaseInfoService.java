package com.example.data.service;

import com.example.data.entity.CaseInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    Page<CaseInfo> selectList(Pageable pageable, String personName);


}
