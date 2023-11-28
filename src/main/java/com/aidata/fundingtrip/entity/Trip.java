package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tboard")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tnum;

    @Column(nullable = false, length = 20)
    private String tmid;

    @Column(nullable = false, length = 50)
    private String ttitle;

    @Column(nullable = false, length = 5000)
    private String tcontents;

    @Column(nullable = false)
    private int tpeople;

    @Column(nullable = false)
    private String tstart;

    @Column(nullable = false)
    private String tend;

    @Column(columnDefinition = "varchar(10) default '대기'")
    private String tstatus;

    @Column(length = 30)
    private String tpic;

    @Column(length = 50)
    private String tsysname;
}
