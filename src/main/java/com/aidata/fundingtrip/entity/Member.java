package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "member")
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mid;

    @Column(nullable = false, length = 20)
    private String mpw;

    @Column(nullable = false, length = 20)
    private String mname;

    @Column(nullable = false, length = 20)
    private String mph;

    @Column(nullable = false, length = 20)
    private String memail;

    @Column(length = 20)
    private String mgrade;

    @Column(length = 30)
    private String mpayment;

}
