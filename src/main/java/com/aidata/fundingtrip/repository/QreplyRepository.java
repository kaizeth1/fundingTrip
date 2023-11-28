package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.Qreply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface QreplyRepository extends JpaRepository<Qreply, Integer> {
}
