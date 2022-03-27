package com.company.data.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("ALL")
@Data
@Entity
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long employeeId;
    private String name;
    private String surname;
    private int age;
    private BigDecimal salary;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdDate;

    private Date updateDate;

    @PrePersist
    public void persist() {
        setCreatedDate(new Date());
    }
}
