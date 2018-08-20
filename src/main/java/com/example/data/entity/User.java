package com.example.data.entity;

import com.example.data.utils.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
public class User extends BaseEntity {
    private String name;
    private String password;
}
