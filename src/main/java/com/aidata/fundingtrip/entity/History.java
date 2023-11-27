package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hisboard")
@Data
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hisnum;

    @Column(nullable = false, length = 20)
    private String hisname;

    @Column(nullable = false, length = 40)
    private String hisloca;

    @Column(nullable = false, length = 30)
    private String hispic;

    @Column(length = 5000)
    private String hisexplan;

    @Column(length = 2000)
    private String hisinfo;

    @Column(length = 50)
    private String hissysname;

}
