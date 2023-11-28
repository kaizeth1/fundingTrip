package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "qboard")
@Data
public class QnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qnum;

    @Column(nullable = false, length = 20)
    private String qmid;

    @Column(nullable = false, length = 50)
    private String qtitle;

    @Column(length = 5000)
    private String qcontents;

    @Column()
    @CreationTimestamp
    private Timestamp qdate;

}
