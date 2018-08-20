package com.example.data.controller;

import com.example.data.config.BaseController;
import com.example.data.config.BaseResult;
import com.example.data.entity.CaseInfo;
import com.example.data.repository.CaseInfoRepository;
import com.example.data.service.CaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author: hzx
 * @Date: 2018/8/13 15:58
 * @Description:
 * @Modify By:
 */
@RestController
@RequestMapping("/v1/CaseInfoController")
public class CaseInfoController extends BaseController{
    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    CaseInfoRepository caseInfoRepository;

    @PostMapping(value = "/addCaseInfo")
    public BaseResult addCaseInfo(String batchNumber, BigDecimal allPayAmount, String personId, String userId){
        caseInfoService.addCaseInfo(batchNumber, allPayAmount, personId, userId);
        return sendResult200();
    }

    @PostMapping(value = "/selectCaseInfo")
    public BaseResult<Page<CaseInfo>> selectCaseInfo(Pageable pageable,String personName) {
        Page<CaseInfo> page = caseInfoService.selectList(pageable,personName);
//        CaseInfo caseInfo = caseInfoRepository.findById(personName).get();
        return sendResult200(page);
    }
}
