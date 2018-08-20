package com.example.data.service.impl;

import com.example.data.entity.CaseInfo;
import com.example.data.entity.Person;
import com.example.data.entity.QCaseInfo;
import com.example.data.entity.User;
import com.example.data.repository.CaseInfoRepository;
import com.example.data.repository.PersonRepository;
import com.example.data.repository.UserRepository;
import com.example.data.service.CaseInfoService;
import com.example.data.utils.Snowflake;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CaseInfoServiceImpl implements CaseInfoService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CaseInfoRepository caseInfoRepository;

    @Override
    public void addCaseInfo(String batchNumber, BigDecimal allPayAmount, String personId, String userId) {
        CaseInfo caseInfo = new CaseInfo();
        Snowflake snowflake = new Snowflake((int) (System.currentTimeMillis() % 1024));
        caseInfo.setCaseNumber(String.valueOf(snowflake.next()));
        caseInfo.setBatchNumber(batchNumber);
        caseInfo.setAllPayAmount(allPayAmount);
        Optional<Person> person = personRepository.findById(personId);
        caseInfo.setPersonInfo(person.get());
        Optional<User> user = userRepository.findById(userId);
        caseInfo.setUserInfo(user.get());
        caseInfoRepository.save(caseInfo);
    }

    @Override
    public Page<CaseInfo> selectList(Pageable pageable, String personName) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QCaseInfo qCaseInfo = QCaseInfo.caseInfo;
        booleanBuilder.and(qCaseInfo.personInfo.name.eq(personName));
        Page<CaseInfo> list = caseInfoRepository.findAll(booleanBuilder,pageable);
        return list;
    }
}
