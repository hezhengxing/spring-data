package com.example.data.service.impl;

import com.example.data.entity.CaseInfo;
import com.example.data.entity.Person;
import com.example.data.entity.User;
import com.example.data.repository.CaseInfoRepository;
import com.example.data.repository.PersonRepository;
import com.example.data.repository.UserRepository;
import com.example.data.service.CaseInfoService;
import com.example.data.utils.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        caseInfo.setPersonId(person.get());
        Optional<User> user = userRepository.findById(userId);
        caseInfo.setUserId(user.get());
        caseInfoRepository.save(caseInfo);
    }
}
