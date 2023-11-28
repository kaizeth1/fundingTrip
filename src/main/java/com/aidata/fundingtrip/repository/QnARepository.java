package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.QnA;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface QnARepository extends JpaRepository<QnA, Integer> {
}
