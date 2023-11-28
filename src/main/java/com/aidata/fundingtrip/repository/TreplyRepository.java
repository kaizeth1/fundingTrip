package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.Treply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface TreplyRepository extends JpaRepository<Treply, Integer> {
}
