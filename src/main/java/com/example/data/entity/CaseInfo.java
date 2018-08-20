package com.example.data.entity;

import com.example.data.utils.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table
@Entity
public class CaseInfo extends BaseEntity{
    private String caseNumber;
    private String batchNumber;
    private BigDecimal allPayAmount;
    @ManyToOne
    @JoinColumn( name = "person_id")
    private Person personId;
    @ManyToOne
    @JoinColumn( name ="user_id")
    private User userId;
}
