package com.aidata.fundingtrip.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "treply")
@Data
public class Treply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trnum;

    @Column(nullable = false)
    private int trbnum;

    @Column(nullable = false, length = 20)
    private String trmid;

    @Column(length = 200)
    private String trcontents;

    @Column()
    @CreationTimestamp
    private Timestamp trdate;
}
