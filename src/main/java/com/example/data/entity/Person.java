package com.example.data.entity;

import com.example.data.utils.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
public class Person extends BaseEntity{
    private String id;
    private String name;
    private String idCard;
    private String phoneNumber;
}
