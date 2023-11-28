package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.Trip;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface TripRepository extends JpaRepository<Trip, Integer> {
}
