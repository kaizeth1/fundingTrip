package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "qreply")
@Data
public class Qreply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qrnum;

    @Column(nullable = false)
    private int qrbnum;

    @Column(nullable = false, length = 20)
    private String qrmid;

    @Column(length = 200)
    private String qrcontents;

    @Column()
    @CreationTimestamp
    private Timestamp qrdate;
}
